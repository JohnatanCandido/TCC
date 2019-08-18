from flask import Flask
from flask_sqlalchemy import SQLAlchemy

from svo.entities.crypt import Crypto

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'postgres+psycopg2://postgres:1234@localhost/svo'
db = SQLAlchemy(app)

c = Crypto()

from svo.endpoints import endpoints
from svo.endpoints.login_endpoint import logins
from svo.endpoints.candidato_endpoint import candidatos
from svo.endpoints.voto_endpoint import votos


app.register_blueprint(logins, url_prefix='/login')
app.register_blueprint(candidatos, url_prefix='/candidato')
app.register_blueprint(votos, url_prefix='/voto')
