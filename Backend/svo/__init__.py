from flask import Flask
from flask_sqlalchemy import SQLAlchemy

from svo.entities.crypt import Crypto

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///teste.db'
db = SQLAlchemy(app)

c = Crypto()

from svo.endpoints import endpoints
