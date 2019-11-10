from threading import Thread
from datetime import datetime
from types import SimpleNamespace
from sqlalchemy import desc

from svo.business import model_factory as mf
from svo.entities.models import Apuracao, Turno, TurnoCargo, TurnoCargoRegiao, Candidato, VotoEncriptado
from svo.exception.validation_exception import ValidationException
from svo.util import database_utils as db


def apurar_eleicao(id_turno):
    turno = db.find_turno(id_turno)
    id_apuracao = insere_apuracao(turno)
    threads = []
    for turno_cargo in turno.turnosCargos:
        for tcr in turno_cargo.turno_cargo_regioes:
            t = Thread(target=apurar_votos, kwargs={'tcr': tcr, 'id_apuracao': id_apuracao})
            threads.append(t)
            t.start()
    for t in threads:
        t.join()
    insere_termino_apuracao(turno.id_turno)
    segundo_turno = verifica_eleitos(turno.id_turno, id_apuracao)
    if segundo_turno and turno.turno == 1:
        cria_segundo_turno(turno.id_eleicao, segundo_turno)


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


def valida_apuracao(id_turno):
    turno = db.find_turno(id_turno)
    if [a for a in turno.apuracoes if a.termino_apuracao is None]:
        msg = 'Esta eleição já está sendo apurada!'
        raise ValidationException(msg, [msg])
    if turno.termino > datetime.now():
        msg = 'A apuração só pode ser feita após o término da votação!'
        raise ValidationException(msg, [msg])


def insere_apuracao(turno):
    apuracao = Apuracao()
    apuracao.id_turno = turno.id_turno
    apuracao.inicio_apuracao = str(datetime.now())
    db.create(apuracao)
    db.commit()

    return apuracao.id_apuracao


def apurar_votos(tcr, id_apuracao):
    print(f'Iniciada apuração: {tcr.turnoCargo.cargo.nome} ===========================================================')
    votos = db.query(VotoEncriptado)\
              .filter(VotoEncriptado.id_turno_cargo_regiao == tcr.id_turno_cargo_regiao)\
              .all()

    for voto_enc in votos:
        voto = mf.cria_voto(voto_enc, id_apuracao)
        db.create(voto)
        db.commit()
    sql = '''UPDATE candidato c 
             SET qt_votos = (SELECT count(*) FROM voto_apurado va 
                             WHERE id_turno_cargo_regiao = :idTurnoCargoRegiao 
                             AND id_apuracao = :idApuracao
                             AND va.id_candidato = c.id_candidato)
             WHERE c.id_turno_cargo_regiao = :idTurnoCargoRegiao'''

    db.native(sql, {'idTurnoCargoRegiao': tcr.id_turno_cargo_regiao, 'idApuracao': id_apuracao})
    db.commit()
    print(f'Finalizada apuração: {tcr.turnoCargo.cargo.nome} =========================================================')


def insere_termino_apuracao(id_turno):
    apuracao = db.apuracao_by_id_turno(id_turno)
    apuracao.termino_apuracao = datetime.now()
    db.commit()


def verifica_eleitos(id_turno, id_apuracao):
    segundo_turno = {}
    turno = db.find_turno(id_turno)
    for turno_cargo in turno.turnosCargos:
        for tcr in turno_cargo.turno_cargo_regioes:
            remove_status_eleito(tcr)
            if tcr.turnoCargo.cargo.sistema_eleicao == 'Maioria Simples':
                verifica_eleito_maioria_simples(tcr, segundo_turno, id_apuracao)
            else:
                verifica_eleito_representacao_proporcional(tcr, id_apuracao)
    return segundo_turno


def verifica_eleito_maioria_simples(tcr, segundo_turno, id_apuracao):
    candidatos = [c for c in tcr.candidatos if c.id_candidato_principal is None]
    candidatos.sort(key=lambda c: c.qt_votos, reverse=True)
    if tcr.possui_segundo_turno and candidatos:
        sql_total_votos = '''SELECT count(*) FROM voto_apurado 
                             WHERE id_turno_cargo_regiao = :idTurnoCargoRegiao 
                             AND id_apuracao = :idApuracao
                             AND id_candidato IS NOT NULL'''

        total_votos = db.native(sql_total_votos, {'idTurnoCargoRegiao': tcr.id_turno_cargo_regiao,
                                                  'idApuracao': id_apuracao}).first()[0]
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


def verifica_eleito_representacao_proporcional(tcr, id_apuracao):
    qt_votos_validos = get_qt_votos_validos(tcr.id_turno_cargo_regiao, id_apuracao)
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


def get_qt_votos_validos(id_tcr, id_apuracao):
    sql_qt_votos_validos = "SELECT count(*) FROM voto_apurado " \
                           "WHERE id_partido IS NOT NULL AND id_turno_cargo_regiao = :idTcr " \
                           "AND id_apuracao = :idApuracao"
    return db.native(sql_qt_votos_validos, {'idTcr': id_tcr, 'idApuracao': id_apuracao}).first()[0]


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
                       .order_by(desc(Candidato.qt_votos))\
                       .limit(partido.cadeiras)\
                       .all()

        for candidato in candidatos:
            candidato.situacao = 'Eleito'
    db.commit()


def remove_status_eleito(tcr):
    sql = """UPDATE candidato SET situacao = 'Não Eleito' WHERE id_turno_cargo_regiao = :idTcr"""
    db.native(sql, {'idTcr': tcr.id_turno_cargo_regiao})
    db.commit()


def verifica_integridade_votos(id_turno):
    sql = '''SELECT COUNT(*) FROM (
                        SELECT DISTINCT id_eleitor FROM (SELECT id_eleitor, id_candidato, id_turno_cargo_regiao 
                                                         FROM voto_encriptado 
                                                         ORDER BY id_eleitor, id_turno_cargo_regiao) ve
             WHERE ve.id_turno_cargo_regiao IN(SELECT id_turno_cargo_regiao
                                            FROM turno_cargo_regiao
                                            JOIN turno_cargo tc ON turno_cargo_regiao.id_turno_cargo = tc.id_turno_cargo
                                            WHERE tc.id_turno = :idTurno)
             GROUP BY ve.id_eleitor
             HAVING md5(string_agg(ve.id_candidato, '')) NOT IN (SELECT et.hash FROM eleitor_turno et)) 
             AS votos_alterados'''

    votos_alterados = db.native(sql, {'idTurno': id_turno}).first()[0]
    if votos_alterados > 0:
        msg = f'{votos_alterados} votos desta eleição foram alterados!'
        raise ValidationException(msg, [msg])


def verifica_remover_segundo_turno(id_turno):
    turno = db.find_turno(id_turno)
    if turno.turno == 1:
        segundo_turno = db.segundo_turno_by_id_eleicao(turno.id_eleicao)
        if segundo_turno is not None:
            db.delete(segundo_turno)
            db.commit()
