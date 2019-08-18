from flask import request, json

from svo import app, c
from svo.business import model_factory as mf, business

# TODO Remover esse arquivo


@app.route("/get_public_key", methods=["GET"])
def get_key():
    return c.get_public_key()


@app.route("/decrypt", methods=["POST"])
def decrypt():
    cypher_text = request.json
    return str(c.dec(cypher_text))


@app.route("/get_res/<id_eleicao>/<id_cargo>", methods=["GET"])
def get_res(id_eleicao, id_cargo):
    return business.pegar_resultado(id_eleicao, id_cargo)


@app.route("/enc", methods=["POST"])
def enc():
    j = request.json
    voto = mf.cria_voto_encriptado(j)
    voto.id_candidato = c.pub.encrypt(int(voto.id_candidato)).ciphertext(be_secure=True)
    return json.dumps(voto.to_json())
