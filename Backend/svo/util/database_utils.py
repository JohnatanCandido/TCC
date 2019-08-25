from svo import db
from svo.entities.models import VotoEncriptado, Login, Pessoa, Cargo, Estado, Cidade, Eleicao


def create(entidade):
    db.session.add(entidade)


def delete(obj):
    db.session.delete(obj)


def expunge(obj):
    db.session.expunge(obj)


def commit():
    db.session.commit()


def query(clazz):
    return db.session.query(clazz)


def find_login(login):
    return Login.query.filter_by(usuario=login.usuario, senha=login.senha).first()


def find_pessoa(id_pessoa):
    return Pessoa.query.get(id_pessoa)


def find_votes(id_eleicao, id_cargo):
    return VotoEncriptado.query.filter_by(id_eleicao=id_eleicao, id_cargo=id_cargo).all()


def find_cargo(id_cargo):
    return Cargo.query.get(id_cargo)


def find_estado(id_estado):
    return Estado.query.get(id_estado)


def find_cidade(id_cidade):
    return Cidade.query.get(id_cidade)


def lista_cargos():
    return Cargo.query.all()


def find_eleicao(id_eleicao):
    return Eleicao.query.get(id_eleicao)
