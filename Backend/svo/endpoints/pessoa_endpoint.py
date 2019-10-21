from flask import request, Blueprint, jsonify

from svo.exception.validation_exception import ValidationException
from svo.util.token_util import protected
from svo.business import pessoa_business

pessoas = Blueprint('pessoas', __name__)


@pessoas.route('/<id_pessoa>', methods=['GET'])
def busca_pessoa(id_pessoa):
    pessoa = pessoa_business.pessoa_by_id(id_pessoa)
    if pessoa is None:
        return jsonify(['Pessoa não encontrada']), 204
    return jsonify(pessoa)


@pessoas.route('/salvar', methods=['POST'])
@protected
def salvar_pessoa(user):
    if not user.login.has_perfil('Administrador'):
        return jsonify(['Você não tem permissão para executar esta ação.']), 403
    pessoa = request.json
    try:
        return pessoa_business.salvar_pessoa(pessoa), 200
    except ValidationException as e:
        return jsonify(e.errors), 400


@pessoas.route('/consultar')
def consultar_pessoas():
    filtro = request.json
    pessoas = pessoa_business.consultar_pessoas(filtro)
    if not pessoas:
        return 'Nenhuma pessoa encontrada.', 204
    return jsonify(pessoas)


@pessoas.route('alterar-senha', methods=['POST'])
@protected
def alterar_senha(user):
    try:
        credenciais = request.json
        pessoa_business.alterar_senha(user, credenciais)
        return 'Senha alterada com sucesso!', 200
    except ValidationException as e:
        return jsonify(e.errors), 400
