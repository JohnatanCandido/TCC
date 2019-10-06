from threading import Thread

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
        return eleicao_business.salvar(eleicao), 200
    except ValidationException as e:
        return jsonify(e.errors), 400


@eleicoes.route('/<id_eleicao>', methods=['GET'])
def consultar_eleicao(id_eleicao):
    eleicao = eleicao_business.eleicao_by_id(id_eleicao)
    if eleicao is None:
        return jsonify(['Eleição não encontrada']), 204
    return jsonify(eleicao), 200


@eleicoes.route('/cargos', methods=['GET'])
def consulta_cargos():
    return jsonify(eleicao_business.consulta_cargos())


@eleicoes.route('/consultar', methods=['GET'])
def consulta_eleicoes():
    filtro = request.json
    eleicoes = eleicao_business.consulta_eleicoes(filtro)
    if not eleicoes:
        return 'Nenhuma eleição encontrada.', 204
    return jsonify(eleicoes), 200


@eleicoes.route('/usuario', methods=['GET'])
@protected
def consulta_eleicoes_usuario(user):
    eleicoes = eleicao_business.consulta_eleicao_por_usuario(user)
    if not eleicoes:
        return 'Nenhuma eleição encontrada.', 204
    return jsonify(eleicoes), 200


@eleicoes.route('/<id_eleicao>/apurar/turno/<turno>')
def apurar(id_eleicao, turno):
    try:
        eleicao_business.valida_apuracao(int(id_eleicao), int(turno))
        thread = Thread(target=eleicao_business.apurar_eleicao, kwargs={'id_eleicao': int(id_eleicao),
                                                                        'num_turno': int(turno)})
        thread.start()
        return 'Foi iniciada a apuração da eleição', 200
    except ValidationException as e:
        return jsonify(e.errors), 400
