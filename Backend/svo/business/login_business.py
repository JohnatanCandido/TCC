from svo.business import model_factory as mf
from svo.util import database_utils, token_util
from svo import c


def auth(dados):
    login = mf.cria_login(dados)
    user = database_utils.find_login(login)
    if user is not None:
        identity = user.to_json()
        identity['token'] = get_token(user)
        return identity
    return None


def get_token(login):
    hash_pessoa = c.enc(login.id_pessoa)
    if login.has_perfil('Administrador'):
        return token_util.generate_adm_token(hash_pessoa)
    return token_util.generate_user_token(hash_pessoa)


def listar_perfis():
    return [p.to_json() for p in database_utils.lista_perfis()]
