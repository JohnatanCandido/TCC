from svo import db
from svo.entities.models import VotoEncriptado, Login, Pessoa


def create(entidade):
    db.session.add(entidade)
    db.session.commit()


def find_login(login):
    return Login.query.filter_by(usuario=login.usuario, senha=login.senha).first()


def find_pessoa(id_pessoa):
    return Pessoa.query.filter_by(id_pessoa=id_pessoa).first()


def find_votes(id_eleicao, id_cargo):
    return VotoEncriptado.query.filter_by(id_eleicao=id_eleicao, id_cargo=id_cargo).all()
