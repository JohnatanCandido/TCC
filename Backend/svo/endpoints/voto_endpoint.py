from flask import request, Blueprint, jsonify

from svo import c
from svo.business import voto_business
from svo.exception.validation_exception import ValidationException
from svo.util.token_util import protected

votos = Blueprint('votos', __name__)


@votos.route("/eleicao/<id_eleicao>/votar", methods=["POST"])
@protected
def votar(user, id_eleicao):
    try:
        votos_json = request.json
        voto_business.votar(user, id_eleicao, votos_json)
        return '200'
    except ValidationException as e:
        return jsonify(e.errors), 403


@votos.route("/cargos/eleicao/<id_eleicao>", methods=['GET'])
@protected
def get_cargos_eleitor(user, id_eleicao):
    try:
        cargos = voto_business.cargos_por_eleicao_e_usuario(id_eleicao, user)
        if not cargos:
            return 'Nenhuma eleição encontrada', 204
        return jsonify(cargos), 200
    except ValidationException as e:
        print(e.errors)
        return jsonify(e.errors), 403


@votos.route("/turno-cargo-regiao/<tcr>/candidato/<numero>")
@protected
def busca_candidato(user, tcr, numero):
    retorno = voto_business.consulta_candidato(tcr, numero)
    if retorno is None:
        return 'Nenhum candidato/partido encontrato', 204
    return jsonify(retorno), 200


@votos.route("/chave-publica", methods=["GET"])
def get_key():
    return c.get_public_key()


@votos.route("/eleitor/pin", methods=['POST'])
@protected
def gera_pin(user):
    voto_business.gerar_pin(user)
    return 'Pin gerado com sucesso!', 200
