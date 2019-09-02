from svo.business import model_factory as mf
from svo.exception.validation_exception import ValidationException
from svo.util import database_utils as db


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
