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
