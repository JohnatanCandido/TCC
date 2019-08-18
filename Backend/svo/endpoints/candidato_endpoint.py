from flask import request, json, Blueprint

from svo.business import candidato_business

candidatos = Blueprint('candidatos', __name__)


@candidatos.route('/cadastrar', methods=['POST'])
def cadastrar():
    if len(request.form) == 0:
        candidato_json = request.json
    else:
        candidato_json = json.loads(request.form['candidato'])
    candidato_business.cadastrar(candidato_json)
