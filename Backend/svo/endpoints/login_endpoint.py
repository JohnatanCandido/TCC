from flask import request, Blueprint, jsonify

from svo.business import login_business

logins = Blueprint('logins', __name__)


@logins.route('/autenticar', methods=['POST'])
def auth():
    login = login_business.auth(request.json)
    return jsonify(login) if login is not None else (jsonify(['Usuário ou senha incorretos']), 401)


@logins.route('/perfis', methods=['GET'])
def listar_perfis():
    return jsonify(login_business.listar_perfis())
