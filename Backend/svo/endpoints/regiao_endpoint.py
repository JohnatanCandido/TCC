from flask import Blueprint, jsonify

from svo.business import regiao_business
from svo.util.token_util import protected

regioes = Blueprint('regioes', __name__)


@regioes.route('/estado/<nome>', methods=['GET'])
@protected
def consulta_estado(user, nome):
    estados = regiao_business.consulta_estado(nome)
    if not estados:
        return 'Nenhum estado encontrado.', 404
    return jsonify(estados)


@regioes.route('/estado/<id_estado>/cidade/<nome>', methods=['GET'])
@protected
def consulta_cidade(user, id_estado, nome):
    cidades = regiao_business.consulta_cidade(id_estado, nome)
    if not cidades:
        return 'Nenhuma cidade encontrada.', 404
    return jsonify(cidades)
