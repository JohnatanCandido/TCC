from flask import request, Blueprint, jsonify

from svo.business import candidato_business
from svo.exception.validation_exception import ValidationException
from svo.util.token_util import protected

candidatos = Blueprint('candidatos', __name__)


@candidatos.route('/cadastrar', methods=['POST'])
@protected
def cadastrar(user):
    if not user.login.has_perfil('Administrador'):
        return jsonify(['Você não tem permissão para executar esta ação.']), 403
    candidato_json = request.json
    try:
        candidato_business.cadastrar(candidato_json)
        return 'Salvo com sucesso', 200
    except ValidationException as e:
        return jsonify(e.errors), 400


@candidatos.route('/partido/<nome>', methods=['GET'])
def busca_partidos(nome):
    partidos = candidato_business.busca_partido(nome)
    if not partidos:
        return 'Nenhum partido encontrado.', 204
    return jsonify(partidos)


@candidatos.route('/partido', methods=['POST'])
@protected
def cadastrar_partido(user):
    if not user.login.has_perfil('Administrador'):
        return jsonify(['Você não tem permissão para executar esta ação.']), 403
    partido_json = request.json
    try:
        candidato_business.cadastrar_partido(partido_json)
        return 'Salvo com sucesso', 200
    except ValidationException as e:
        return jsonify(e.errors), 400


@candidatos.route('/turnoCargoRegiao/<id_turno_cargo_regiao>', methods=['GET'])
def busca_candidatos(id_turno_cargo_regiao):
    candidatos = candidato_business.busca_candidatos(id_turno_cargo_regiao)
    if not candidatos:
        return 'Nenhum candidato encontrado', 204
    return jsonify(candidatos)
