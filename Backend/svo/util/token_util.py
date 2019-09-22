import jwt
from datetime import datetime, timedelta
from functools import wraps
from flask import request
from jwt import ExpiredSignatureError

from svo.util import database_utils
from svo import c

secret = 'CRIAR UM SECRET'  # TODO Colocar em um lugar seguro


def generate_adm_token(id_pessoa):
    return generate_token(id_pessoa, timedelta(hours=5))


def generate_user_token(id_pessoa):
    return generate_token(id_pessoa, timedelta(minutes=30))


def generate_token(id_pessoa, expiration_time):
    payload = {
        'idPessoa': id_pessoa,
        'exp': datetime.utcnow() + expiration_time
    }
    return jwt.encode(payload, secret, algorithm='HS256').decode('utf-8')


def validate_token(encoded_jwt, id_pessoa):
    decoded = jwt.decode(encoded_jwt.encode(), secret, algorithms=['HS256'])
    return decoded['idPessoa'] == id_pessoa


def protected(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = None

        if 'Authorization' in request.headers:
            token = request.headers['Authorization'].replace('Bearer ', '').encode()

        if not token:
            return 'Login necessário', 401

        try:
            data = jwt.decode(token, secret, algorithms=['HS256'])
            id_pessoa = c.dec(data['idPessoa'])
            user = database_utils.find_pessoa(id_pessoa)
        except ExpiredSignatureError:
            return 'Sessão inválida', 401
        return f(user, *args, **kwargs)
    return decorated
