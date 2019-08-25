from flask import request, Blueprint, jsonify

from svo.exception.validation_exception import ValidationException
from svo.util.token_util import protected
from svo.business import eleicao_business

eleicoes = Blueprint('eleicoes', __name__)


@eleicoes.route('/salvar', methods=['POST'])
@protected
def salvar(user):
    if not user.login.has_perfil('Administrador'):
        return jsonify(['Você não tem permissão para executar esta ação.']), 403
    eleicao = request.json
    try:
        eleicao_business.salvar(eleicao)
        return 'Salvo com sucesso', 200
    except ValidationException as e:
        return jsonify(e.errors), 400


@eleicoes.route('/<id_eleicao>', methods=['GET'])
@protected
def consultar_eleicao(user, id_eleicao):
    eleicao = eleicao_business.eleicao_by_id(id_eleicao)
    if eleicao is None:
        return jsonify(['Eleição não encontrada']), 404
    return jsonify(eleicao)


@eleicoes.route('/cargos', methods=['GET'])
@protected
def consulta_cargos(user):
    return jsonify(eleicao_business.consulta_cargos())