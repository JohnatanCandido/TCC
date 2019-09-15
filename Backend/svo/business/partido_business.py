from sqlalchemy import or_

from svo.entities.models import Coligacao, Partido
from svo.exception.validation_exception import ValidationException
from svo.business import model_factory as mf
from svo.util import database_utils as db


def cadastrar_partido(dados):
    partido = mf.cria_partido(dados)
    validar_partido(partido)
    if partido.id_partido is None:
        db.create(partido)
    db.commit()


def validar_partido(partido):
    errors = []
    if partido.numero_partido is None:
        errors.append("O número do partido é obrigatório")
    if partido.sigla is None:
        errors.append("A sigla do partido é obrigatória")
    if partido.nome is None:
        errors.append("O nome do partido é obrigatório")
    if errors:
        raise ValidationException('Erros de validação', errors)


def cadastrar_coligacao(dados):
    coligacao = mf.cria_coligacao(dados)
    validar_coligacao(coligacao)
    if coligacao.id_coligacao is None:
        db.create(coligacao)
    db.commit()
    return str(coligacao.id_coligacao)


def validar_coligacao(coligacao):
    errors = []
    if coligacao.nome is None:
        errors.append("O nome da coligação é obrigatório")
    if coligacao.id_eleicao is None:
        errors.append("A coligação deve estar vinculada a uma eleição")
    if errors:
        raise ValidationException('Erros de validação', errors)


def buscar_coligacoes(id_eleicao):
    coligacoes = db.query(Coligacao).filter(Coligacao.id_eleicao == id_eleicao).all()
    if not coligacoes:
        return []
    return [c.to_json() for c in coligacoes]


def busca_partido(nome):
    nome = nome.replace('+', '%')
    try:
        nome = int(nome)
        partidos = db.query(Partido).filter(Partido.numero_partido == int(nome)).all()
    except ValueError:
        partidos = db.query(Partido).filter(or_(Partido.nome.ilike(f'%{nome}%'),
                                                Partido.sigla.ilike(f'%{nome}%'))).all()
    if not partidos:
        return []
    return [p.to_json() for p in partidos]


def buscar_partidos(id_coligacao):
    partidos = db.query(Partido).join(Partido.coligacoes).filter(Coligacao.id_coligacao == id_coligacao).all()
    if not partidos:
        return []
    return [p.to_json() for p in partidos]
