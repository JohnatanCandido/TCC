from flask import request, json

from svo import app, c
from svo.business import model_factory as mf, business

# TODO Remover esse arquivo


@app.route("/decrypt", methods=["POST"])
def decrypt():
    cypher_text = request.json
    return str(c.dec(cypher_text))


@app.route("/get_res/<id_eleicao>/<id_cargo>", methods=["GET"])
def get_res(id_eleicao, id_cargo):
    return business.pegar_resultado(id_eleicao, id_cargo)