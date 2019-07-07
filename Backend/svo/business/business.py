from svo.business import database_utils as db_utils
from svo import c


def pegar_resultado(id_eleicao, id_cargo):
    votos = db_utils.find_votes(id_eleicao, id_cargo)

    m = 1
    for v in votos:
        m *= int(v.id_candidato)
    enc_res = m % c.pub.nsquare
    return str(c.dec(enc_res))
