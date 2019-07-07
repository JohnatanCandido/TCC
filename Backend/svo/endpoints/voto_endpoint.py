from flask import request, json

from svo import app
from svo.business import model_factory as mf, database_utils as db_util


@app.route("/votar", methods=["POST"])
def vote():
    if len(request.form) == 0:
        voto_json = request.json
    else:
        voto_json = json.loads(request.form['voto'])
    voto = mf.cria_voto_encriptado(voto_json)
    db_util.create(voto)
    return '200'
