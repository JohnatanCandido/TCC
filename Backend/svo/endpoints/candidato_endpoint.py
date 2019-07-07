from flask import request, json

from svo import app
from svo.business import candidato_business


@app.route('/candidato/cadastrar', methods=['POST'])
def cadastrar():
    if len(request.form) == 0:
        candidato_json = request.json
    else:
        candidato_json = json.loads(request.form['candidato'])
    candidato_business.cadastrar(candidato_json)
