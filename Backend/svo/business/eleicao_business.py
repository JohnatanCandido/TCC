from threading import Thread
from datetime import datetime
from types import SimpleNamespace

from svo.business import model_factory as mf
from svo.exception.validation_exception import ValidationException
from svo.util import database_utils as db

# Usado na consulta de eleições
# noinspection PyUnresolvedReferences
from svo.entities.models import Eleicao, VotoApurado, Apuracao, Turno, TurnoCargo, TurnoCargoRegiao, Candidato, \
    VotoEncriptado


def consulta_cargos():
    return {'cargos': [c.to_json() for c in db.lista_cargos()]}


def consulta_eleicoes(filtro):
    if 'idEleicao' in filtro:
        eleicao = db.find_eleicao(filtro['idEleicao'])
        if eleicao is None:
            return []
        return [eleicao.campos_consulta()]
    query = 'db.query(Eleicao)'
    if 'data' in filtro:
        query += '.join(Eleicao.turnos).filter(Turno.inicio.cast(Date) == filtro["data"])'
    if 'titulo' in filtro:
        query += ".filter(Eleicao.titulo.ilike(f'%{filtro[\"titulo\"]}%'))"
    query += '.all()'
    eleicoes = eval(query)
    if not eleicoes:
        return []
    return [e.campos_consulta() for e in eleicoes]


def eleicao_by_id(id_eleicao):
    eleicao = db.find_eleicao(id_eleicao)
    if eleicao is None:
        return None
    return eleicao.to_json()


def salvar(dados):
    eleicao = mf.cria_eleicao(dados)
    validar_eleicao(eleicao)
    if eleicao.id_eleicao is None:
        db.create(eleicao)
    db.commit()
    return str(eleicao.id_eleicao)


def validar_eleicao(eleicao):
    errors = []
    if eleicao.titulo is None:
        errors.append("O título é obrigatório")
    for turno in eleicao.turnos:
        validar_turno(turno, errors)
    if len(errors) > 0:
        raise ValidationException('Erros de validação', errors)


def validar_turno(turno, errors):
    if turno.inicio is None:
        errors.append(f"A data de início do {turno.turno}º turno é obrigatória")
    if turno.termino is None:
        errors.append(f"A data de término do {turno.turno}º turno é obrigatória")
    if len(turno.turnosCargos) == 0:
        errors.append(f"É obrigatório adicionar pelo menos um cargo no {turno.turno}º turno")
    else:
        for turnoCargo in turno.turnosCargos:
            validar_turno_cargo(turnoCargo, errors)


def validar_turno_cargo(turno_cargo, errors):
    if len(turno_cargo.turno_cargo_regioes) == 0:
        errors.append(f"É necessário adicionar pelo menos uma regiao para o cargo {turno_cargo.cargo.nome}")


def consulta_eleicao_por_usuario(user):
    sql = '''SELECT DISTINCT e.id_eleicao, e.titulo, t.inicio, t.termino, t.turno, et IS NOT NULL AS votou 
             FROM eleicao e
             JOIN turno t ON e.id_eleicao = t.id_eleicao
             JOIN turno_cargo tc ON t.id_turno = tc.id_turno
             JOIN turno_cargo_regiao tcr ON tc.id_turno_cargo = tcr.id_turno_cargo
             LEFT JOIN eleitor_turno et ON t.id_turno = et.id_turno AND et.id_eleitor = :idEleitor
             WHERE t.inicio IS NOT NULL AND t.termino IS NOT NULL AND e.confirmada
             AND ((tcr.id_estado IS NULL AND tcr.id_cidade IS NULL)
                   OR (tcr.id_estado = :idEstado AND tcr.id_cidade IS NULL)
                   OR (tcr.id_estado = :idEstado AND tcr.id_cidade = :idCidade))'''

    resultado = db.native(sql, {'idEstado': user.eleitor.cidade.id_estado,
                                'idCidade': user.eleitor.id_cidade,
                                'idEleitor': user.eleitor.id_eleitor})

    eleicoes = []
    for r in resultado:
        eleicoes.append({'idEleicao': r['id_eleicao'],
                         'titulo': r['titulo'],
                         'inicio': str(r['inicio']),
                         'termino': str(r['termino']),
                         'turno': r['turno'],
                         'votou': r['votou']})
    return eleicoes


# APURAÇÃO =============================================================================================================

def apurar_eleicao(id_eleicao, num_turno):
    turno = db.find_eleicao(id_eleicao).turnos[num_turno-1]
    insere_apuracao(turno)
    threads = []
    for turno_cargo in turno.turnosCargos:
        for tcr in turno_cargo.turno_cargo_regioes:
            t = Thread(target=apurar_votos, kwargs={'tcr': tcr})
            threads.append(t)
            t.start()
    for t in threads:
        t.join()
    insere_termino_apuracao(turno.id_turno)
    segundo_turno = verifica_eleitos(turno.id_turno)
    if segundo_turno:
        cria_segundo_turno(id_eleicao, segundo_turno)


def cria_segundo_turno(id_eleicao, segundo_turno):
    turno = Turno()
    turno.id_eleicao = id_eleicao
    turno.turno = 2
    cria_turno_cargo(turno, segundo_turno)
    db.create(turno)
    db.commit()


def cria_turno_cargo(turno, segundo_turno):
    for tc, tcrs in segundo_turno.items():
        turno_cargo = TurnoCargo()
        turno_cargo.turno = turno
        turno_cargo.id_cargo = tc.id_cargo
        turno.turnosCargos.append(turno_cargo)
        cria_turno_cargo_regiao(turno_cargo, tcrs)


def cria_turno_cargo_regiao(turno_cargo, tcrs):
    for tcr, candidatos in tcrs.items():
        turno_cargo_regiao = TurnoCargoRegiao()
        turno_cargo_regiao.turnoCargo = turno_cargo
        turno_cargo.turno_cargo_regioes.append(turno_cargo_regiao)
        turno_cargo_regiao.qtd_cadeiras = 1
        turno_cargo_regiao.possui_segundo_turno = False
        turno_cargo_regiao.id_estado = tcr.id_estado
        turno_cargo_regiao.id_cidade = tcr.id_cidade
        cria_candidatos(turno_cargo_regiao, candidatos)


def cria_candidatos(turno_cargo_regiao, candidatos):
    for c in candidatos:
        candidato = Candidato()
        candidato.numero = c.numero
        candidato.id_partido = c.id_partido
        candidato.id_pessoa = c.id_pessoa
        candidato.turnoCargoRegiao = turno_cargo_regiao
        turno_cargo_regiao.candidatos.append(candidato)
        vice = Candidato()
        vice.numero = c.vice.numero
        vice.id_partido = c.vice.id_partido
        vice.id_pessoa = c.vice.id_pessoa
        vice.turnoCargoRegiao = turno_cargo_regiao
        turno_cargo_regiao.candidatos.append(vice)
        candidato.vice = vice
        vice.candidato_principal = candidato


def valida_apuracao(id_eleicao, turno):
    turno = db.find_eleicao(id_eleicao).turnos[turno - 1]
    if turno.apuracao is not None:
        msg = f'O {turno.turno}º desta eleição já foi apurado!'
        raise ValidationException(msg, [msg])


def insere_apuracao(turno):
    id_turno = turno.id_turno
    apuracao = Apuracao()
    apuracao.id_turno = id_turno
    apuracao.inicio_apuracao = str(datetime.now())
    db.create(apuracao)
    db.commit()


def apurar_votos(tcr):
    print(f'Iniciada apuração: {tcr.turnoCargo.cargo.nome} ===========================================================')
    votos = db.query(VotoEncriptado)\
              .filter(VotoEncriptado.id_turno_cargo_regiao == tcr.id_turno_cargo_regiao)\
              .filter(VotoEncriptado.voto_apurado == None)\
              .all()

    for voto_enc in votos:
        voto = mf.cria_voto(voto_enc)
        db.create(voto)
        db.commit()
    sql = '''UPDATE candidato c 
             SET qt_votos = (SELECT count(*) FROM voto_apurado va 
                             WHERE id_turno_cargo_regiao = :idTurnoCargoRegiao 
                             AND va.id_candidato = c.id_candidato)
             WHERE c.id_turno_cargo_regiao = :idTurnoCargoRegiao'''

    db.native(sql, {'idTurnoCargoRegiao': tcr.id_turno_cargo_regiao})
    db.commit()
    print(f'Finalizada apuração: {tcr.turnoCargo.cargo.nome} =========================================================')


def insere_termino_apuracao(id_turno):
    apuracao = db.apuracao_by_id_turno(id_turno)
    apuracao.termino_apuracao = datetime.now()
    db.commit()


def verifica_eleitos(id_turno):
    segundo_turno = {}
    turno = db.find_turno(id_turno)
    for turno_cargo in turno.turnosCargos:
        for tcr in turno_cargo.turno_cargo_regioes:
            if tcr.turnoCargo.cargo.sistema_eleicao == 'Maioria Simples':
                verifica_eleito_maioria_simples(tcr, segundo_turno)
            else:
                verifica_eleito_representacao_proporcional(tcr)
    return segundo_turno


def verifica_eleito_maioria_simples(tcr, segundo_turno):
    candidatos = tcr.candidatos
    candidatos.sort(key=lambda c: c.qt_votos, reverse=True)
    if tcr.possui_segundo_turno and candidatos:
        sql_total_votos = '''SELECT count(*) FROM voto_apurado 
                             WHERE id_turno_cargo_regiao = :idTurnoCargoRegiao 
                             AND id_candidato IS NOT NULL'''

        total_votos = db.native(sql_total_votos, {'idTurnoCargoRegiao': tcr.id_turno_cargo_regiao}).first()[0]
        if candidatos[0].qt_votos > (int(total_votos/2)):
            candidatos[0].situacao = 'Eleito'
        else:
            candidatos[0].situacao = 'Segundo Turno'
            candidatos[1].situacao = 'Segundo Turno'
            adiciona_turno_cargo_regiao_segundo_turno(tcr, candidatos[:2], segundo_turno)
    elif candidatos:
        for i in range(tcr.qtd_cadeiras):
            candidatos[i].situacao = 'Eleito'
    db.commit()


def adiciona_turno_cargo_regiao_segundo_turno(tcr, candidatos, segundo_turno):
    if tcr.turnoCargo not in segundo_turno:
        segundo_turno[tcr.turnoCargo] = {}
    segundo_turno[tcr.turnoCargo][tcr] = candidatos


def verifica_eleito_representacao_proporcional(tcr):
    qt_votos_validos = get_qt_votos_validos(tcr.id_turno_cargo_regiao)
    if qt_votos_validos > 0:
        quociente_eleitoral = int(qt_votos_validos / tcr.qtd_cadeiras)
        partidos = get_partidos(tcr.id_turno_cargo_regiao)

        cadeiras_preenchidas = verifica_total_cadeiras_preenchidas(tcr.id_turno_cargo_regiao, partidos, quociente_eleitoral)
        sobras = tcr.qtd_cadeiras - cadeiras_preenchidas
        for i in range(sobras):
            partidos_sobras = [p for p in partidos if p.qt_candidatos_elegiveis > p.cadeiras]
            for partido in partidos_sobras:
                partido.simulacao_cadeiras = partido.votos / (partido.cadeiras + 1)
            partidos_sobras.sort(key=lambda p: p.simulacao_cadeiras, reverse=True)
            partidos_sobras[0].cadeiras += 1
        setar_candidatos_eleitos_representacao_proporcional(tcr.id_turno_cargo_regiao, partidos)


def get_qt_votos_validos(id_tcr):
    sql_qt_votos_validos = "SELECT count(*) FROM voto_apurado " \
                           "WHERE id_partido IS NOT NULL AND id_turno_cargo_regiao = :idTcr"
    return db.native(sql_qt_votos_validos, {'idTcr': id_tcr}).first()[0]


def get_partidos(id_tcr):
    sql_partidos = 'SELECT partido.id_partido, sum(qt_votos) ' \
                   'FROM partido JOIN candidato ON partido.id_partido = candidato.id_partido ' \
                   'WHERE id_turno_cargo_regiao = :idTcr ' \
                   'GROUP BY partido.id_partido'

    partidos = db.native(sql_partidos, {'idTcr': id_tcr}).fetchall()
    return [SimpleNamespace(id_partido=p[0], votos=p[1]) for p in partidos]


def verifica_total_cadeiras_preenchidas(id_tcr, partidos, quociente_eleitoral):
    total_cadeiras_preenchidas = 0
    for partido in partidos:
        partido.cadeiras = int(partido.votos / quociente_eleitoral)
        sql_candidatos_elegiveis = 'SELECT count(*) FROM candidato ' \
                                   'WHERE candidato.id_turno_cargo_regiao = :idTcr ' \
                                   'AND candidato.id_partido = :idPartido ' \
                                   'AND candidato.qt_votos >= :quocienteEleitoral'
        params = {
            'idTcr': id_tcr,
            'idPartido': partido.id_partido,
            'quocienteEleitoral': quociente_eleitoral * 0.1
        }
        qt_candidatos_elegiveis = db.native(sql_candidatos_elegiveis, params).first()[0]
        if qt_candidatos_elegiveis < partido.cadeiras:
            total_cadeiras_preenchidas += qt_candidatos_elegiveis
            partido.cadeiras = qt_candidatos_elegiveis
        else:
            total_cadeiras_preenchidas += partido.cadeiras
        partido.qt_candidatos_elegiveis = qt_candidatos_elegiveis
    return total_cadeiras_preenchidas


def setar_candidatos_eleitos_representacao_proporcional(id_tcr, partidos):
    for partido in partidos:
        candidatos = db.query(Candidato)\
                       .filter(Candidato.id_turno_cargo_regiao == id_tcr)\
                       .filter(Candidato.id_partido == partido.id_partido)\
                       .limit(partido.cadeiras)\
                       .all()

        for candidato in candidatos:
            candidato.situacao = 'Eleito'
    db.commit()


def confirmar_eleicao(id_eleicao):
    eleicao = db.find_eleicao(id_eleicao)
    eleicao.confirmada = True
    db.commit()
