from svo.business import model_factory as mf, database_utils as db, validation


def cadastrar(candidato_json):
    candidato = mf.cria_candidato(candidato_json)
    validation.validar(candidato)
    db.create(candidato)
