from svo.entities.models import Cidade, Estado
from svo.util import database_utils as db


def consulta_cidade(id_estado, nome):
    cidades = db.query(Cidade).filter(Cidade.id_estado == id_estado, Cidade.nome.ilike(f'%{nome}%')).all()
    return [c.to_json() for c in cidades]


def consulta_estado(nome):
    estados = db.query(Estado).filter(Estado.nome.ilike(f'%{nome}%')).all()
    return [e.to_json() for e in estados]
