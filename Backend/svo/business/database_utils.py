from svo import db
from svo.entities.models import VotoEncriptado


def create(entidade):
    db.session.add(entidade)
    db.session.commit()


def find_votes(id_eleicao, id_cargo):
    return VotoEncriptado.query.filter_by(id_eleicao=id_eleicao, id_cargo=id_cargo).all()
