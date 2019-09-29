from threading import Thread
from datetime import datetime

from svo.business import model_factory as mf
from svo.exception.validation_exception import ValidationException
from svo.util import database_utils as db

# Usado na consulta de eleições
# noinspection PyUnresolvedReferences
from svo.entities.models import Eleicao, VotoApurado, Apuracao


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
    sql = '''SELECT DISTINCT e.id_eleicao, e.titulo, t.inicio, t.termino, et IS NOT NULL AS votou 
             FROM eleicao e
             JOIN turno t ON e.id_eleicao = t.id_eleicao
             JOIN turno_cargo tc ON t.id_turno = tc.id_turno
             JOIN turno_cargo_regiao tcr ON tc.id_turno_cargo = tcr.id_turno_cargo
             LEFT JOIN eleitor_turno et ON t.id_turno = et.id_turno AND et.id_eleitor = :idEleitor
             WHERE ((tcr.id_estado IS NULL AND tcr.id_cidade IS NULL)
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
                         'votou': r['votou']})
    return eleicoes


def apurar_eleicao(id_eleicao, turno):
    turno = db.find_eleicao(id_eleicao).turnos[turno-1]
    insere_apuracao(turno)
    for turno_cargo in turno.turnosCargos:
        for tcr in turno_cargo.turno_cargo_regioes:
            Thread(target=apurar_votos, kwargs={'tcr': tcr}).start()


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
    for voto_enc in tcr.votosEncriptados:
        voto = mf.cria_voto(voto_enc)
        db.create(voto)
        db.commit()
    verifica_apuracao_finalizada(tcr.turnoCargo.id_turno)
    print(f'Finalizada apuração: {tcr.turnoCargo.cargo.nome} =========================================================')


def verifica_apuracao_finalizada(id_turno):
    sql = '''SELECT NOT exists(SELECT 1 FROM voto_encriptado ve 
                               JOIN turno_cargo_regiao USING(id_turno_cargo_regiao)
                               JOIN turno_cargo USING(id_turno_cargo)
                               JOIN turno USING(id_turno)
                               WHERE NOT exists(SELECT 1 FROM voto_apurado va
                                                WHERE va.id_voto_encriptado = ve.id_voto_encriptado)
                               AND turno.id_turno = :idTurno)'''

    finalizado = db.native(sql, {'idTurno': id_turno}).first()[0]
    if finalizado:
        apuracao = db.apuracao_by_id_turno(id_turno)
        apuracao.termino_apuracao = datetime.now()
        db.commit()
