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


def eleicao_by_id(user, id_eleicao):
    eleicao = db.find_eleicao(id_eleicao)
    if eleicao is None:
        return None
    eleicao_json = eleicao.to_json()
    for turno in eleicao_json['turnos']:
        hash_eleitor = db.hash_eleitor_turno(turno['idTurno'], user.eleitor.id_eleitor)
        turno['hashEleitor'] = hash_eleitor
    return eleicao_json


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


def confirmar_eleicao(id_eleicao):
    eleicao = db.find_eleicao(id_eleicao)
    eleicao.confirmada = True
    db.commit()
