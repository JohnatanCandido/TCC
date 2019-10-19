from flask import request, Blueprint

from svo import c
from svo.business import business
from svo.testes import teste
from svo.util import email_util


# TODO Remover esse arquivo

endpoints = Blueprint('endpoints', __name__)


@endpoints.route("/decrypt", methods=["POST"])
def decrypt():
    cypher_text = request.json
    return str(c.dec(cypher_text))


@endpoints.route("/get_res/<id_eleicao>/<id_cargo>", methods=["GET"])
def get_res(id_eleicao, id_cargo):
    return business.pegar_resultado(id_eleicao, id_cargo)


@endpoints.route('/pessoas', methods=['GET'])
def cria_pessoas():
    teste.cria_pessoas()
    return 'Pessoas criadas', 200


@endpoints.route('/tcr/<id_tcr>/criar-candidatos/qt/<qt>')
def cria_candidatos(id_tcr, qt):
    teste.cria_candidatos(int(id_tcr), int(qt))
    return 'Candidatos criados', 200


@endpoints.route('/votar/<id_eleicao>', methods=['GET'])
def votar(id_eleicao):
    teste.votar(id_eleicao)
    return 'OK', 200


@endpoints.route('/teste-email', methods=['GET'])
def envia_email():
    email_util.enviar_email('johnatanespindola@gmail.com', 'Teste msg', 'Teste assunto')
    return 'ok', 200
