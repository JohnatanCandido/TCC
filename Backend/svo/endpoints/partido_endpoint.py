from flask import request, Blueprint, jsonify

from svo.business import partido_business
from svo.exception.validation_exception import ValidationException
from svo.util.token_util import protected

partidos = Blueprint('partidos', __name__)


@partidos.route('/cadastrar', methods=['POST'])
@protected
def cadastrar_partido(user):
    if not user.login.has_perfil('Administrador'):
        return jsonify(['Você não tem permissão para executar esta ação.']), 403
    partido_json = request.json
    try:
        partido_business.cadastrar_partido(partido_json)
        return 'Salvo com sucesso', 200
    except ValidationException as e:
        return jsonify(e.errors), 400


@partidos.route('/coligacao', methods=['POST'])
@protected
def cadastrar_coligacao(user):
    if not user.login.has_perfil('Administrador'):
        return jsonify(['Você não tem permissão para executar esta ação.']), 403
    coligacao_json = request.json
    try:
        return partido_business.cadastrar_coligacao(coligacao_json), 200
    except ValidationException as e:
        return jsonify(e.errors), 400


# noinspection PyShadowingNames
@partidos.route('/<nome>', methods=['GET'])
def busca_partido(nome):
    partidos = partido_business.busca_partido(nome)
    if not partidos:
        return 'Nenhum partido encontrado.', 204
    return jsonify(partidos)


@partidos.route('/coligacao/eleicao/<id_eleicao>', methods=['GET'])
def buscar_coligacoes(id_eleicao):
    coligacoes = partido_business.buscar_coligacoes(id_eleicao)
    if not coligacoes:
        return 'Nenhuma coligação encontrada.', 204
    return jsonify(coligacoes)


# noinspection PyShadowingNames
@partidos.route('/coligacao/<id_coligacao>', methods=['GET'])
def buscar_partidos(id_coligacao):
    partidos = partido_business.buscar_partidos(id_coligacao)
    if not partidos:
        return 'Nenhum partido encontrato', 204
    return jsonify(partidos)
