from flask import request, jsonify, Blueprint

from svo import c
from svo.business import business
from svo.testes import teste


# TODO Remover esse arquivo

endpoints = Blueprint('endpoints', __name__)


@endpoints.route("/decrypt", methods=["POST"])
def decrypt():
    cypher_text = request.json
    return str(c.dec(cypher_text))


@endpoints.route("/get_res/<id_eleicao>/<id_cargo>", methods=["GET"])
def get_res(id_eleicao, id_cargo):
    return business.pegar_resultado(id_eleicao, id_cargo)


@endpoints.route('/testar/<id_eleicao>', methods=['GET'])
def testar(id_eleicao):
    teste.testar(id_eleicao)
    return 'OK', 200
