from datetime import datetime

from sqlalchemy.sql import text
from sqlalchemy import desc

from svo import db
from svo.entities.models import VotoEncriptado, Login, Pessoa, Cargo, Estado, Cidade, Eleicao, Perfil, Partido, \
    Coligacao, TurnoCargoRegiao, Turno, Apuracao, EleitorTurno


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


def native(sql, params):
    return db.engine.execute(text(sql), params)


def find_login(usuario, senha):
    return Login.query.filter_by(usuario=usuario, senha=senha).first()


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


def lista_perfis():
    return Perfil.query.all()


def find_perfil(id_perfil):
    return Perfil.query.get(id_perfil)


def find_partido(id_partido):
    return Partido.query.get(id_partido)


def find_coligacao(id_coligacao):
    return Coligacao.query.get(id_coligacao)


def find_turno_cargo_regiao(id_turno_cargo_regiao):
    return TurnoCargoRegiao.query.get(id_turno_cargo_regiao)


def find_turno(id_turno):
    return Turno.query.get(id_turno)


def apuracao_by_id_turno(id_turno):
    return query(Apuracao).filter(Apuracao.id_turno == id_turno).order_by(desc(Apuracao.inicio_apuracao)).first()


def hash_eleitor_turno(id_turno, id_eleitor):
    eleitor_turno = query(EleitorTurno).filter(EleitorTurno.id_turno == id_turno)\
                                       .filter(EleitorTurno.id_eleitor == id_eleitor)\
                                       .first()

    return eleitor_turno.hash if eleitor_turno is not None else None


def busca_pin_valido_por_id_eleitor(id_eleitor):
    sql = """SELECT pin FROM pin_eleitor pe 
             WHERE pe.id_eleitor = :idEleitor AND extract(epoch from (:hora - criacao)) < 300"""
    pin = native(sql, {'idEleitor': id_eleitor, 'hora': str(datetime.now())}).fetchall()
    if not pin:
        return None
    return pin[0][0]
