from svo.business import model_factory as mf, validation
from svo.util import database_utils as db


def cadastrar(candidato_json):
    candidato = mf.cria_candidato(candidato_json)
    validation.validar(candidato)
    db.create(candidato)
    db.commit()
