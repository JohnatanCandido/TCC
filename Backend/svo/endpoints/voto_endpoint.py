from flask import request, json, Blueprint

from svo.business import model_factory as mf
from svo.util import database_utils as db_util

votos = Blueprint('votos', __name__)


@votos.route("/votar", methods=["POST"])
def vote():
    if len(request.form) == 0:
        voto_json = request.json
    else:
        voto_json = json.loads(request.form['voto'])
    voto = mf.cria_voto_encriptado(voto_json)
    db_util.create(voto)
    return '200'
