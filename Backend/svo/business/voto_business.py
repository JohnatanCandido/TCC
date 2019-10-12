from datetime import datetime

from svo import c
from svo.business import model_factory as mf
from svo.entities.models import Candidato, Partido, PinEleitor
from svo.exception.validation_exception import ValidationException
from svo.util import database_utils as db, senha_util, email_util


def cargos_por_eleicao_e_usuario(id_eleicao, user):
    id_turno = valida_usuario_votou_e_retorna_turno_aberto(id_eleicao, user)

    sql = '''SELECT DISTINCT id_turno_cargo_regiao, c.nome, c.max_votos FROM turno t
             JOIN turno_cargo tc ON t.id_turno = tc.id_turno
             JOIN cargo c ON tc.id_cargo = c.id_cargo
             JOIN turno_cargo_regiao tcr ON tc.id_turno_cargo = tcr.id_turno_cargo
             WHERE ((tcr.id_estado IS NULL AND tcr.id_cidade IS NULL)
                 OR (tcr.id_estado = :idEstado AND tcr.id_cidade IS NULL)
                 OR (tcr.id_estado = :idEstado AND tcr.id_cidade = :idCidade))
             AND t.id_turno = :idTurno'''
    resultado = db.native(sql, {'idTurno': id_turno,
                                'idEstado': user.eleitor.cidade.id_estado,
                                'idCidade': user.eleitor.id_cidade})

    cargos = []
    for r in resultado:
        for v in range(r['max_votos']):
            cargos.append({
                'idTurnoCargoRegiao': r['id_turno_cargo_regiao'],
                'nomeCargo': r['nome'],
                'index_voto': v+1
            })
    return cargos


def valida_usuario_votou_e_retorna_turno_aberto(id_eleicao, user):
    id_turno = consulta_turno_aberto_por_eleicao(id_eleicao)

    if id_turno is None:
        msg = 'Não há nenhum turno em andamento para esta eleição'
        raise ValidationException(msg, [msg])
    sql_votou = 'SELECT EXISTS(SELECT 1 FROM eleitor_turno ' \
                '              WHERE id_eleitor = :idEleitor ' \
                '              AND id_turno = :idTurno) AS votou'
    votou = db.native(sql_votou, {'idTurno': id_turno, 'idEleitor': user.eleitor.id_eleitor}).first()['votou']

    if votou:
        msg = 'Voce já votou nesta eleição'
        raise ValidationException(msg, [msg])
    return id_turno


def consulta_turno_aberto_por_eleicao(id_eleicao):
    sql = '''SELECT t.id_turno FROM turno t 
             LEFT JOIN apuracao a ON a.id_turno = t.id_turno
             WHERE current_date BETWEEN t.inicio AND t.termino
             AND a IS NULL 
             AND t.id_eleicao = :idEleicao'''

    result = db.native(sql, {'idEleicao': id_eleicao})
    id_turno = result.first()['id_turno']
    return id_turno


def consulta_candidato(tcr, numero):
    candidato = db.query(Candidato)\
                  .filter(Candidato.id_turno_cargo_regiao == tcr)\
                  .filter(Candidato.numero == numero)\
                  .first()

    if candidato is None:
        partido = db.query(Partido).filter(Partido.numero_partido == numero).first()
        if partido is None:
            return None
        retorno = monta_partido(tcr, partido)
    else:
        retorno = monta_candidato(candidato)
    return retorno


def monta_partido(tcr, partido):
    candidatos = db.query(Candidato) \
                   .filter(Candidato.id_turno_cargo_regiao == tcr) \
                   .filter(Candidato.id_partido == partido.id_partido) \
                   .all()
    if not candidatos:
        return None
    return {'idPartido': partido.id_partido,
            'nome': partido.nome}


def monta_candidato(candidato):
    retorno = {'idCandidato': candidato.id_partido,
               'nome': candidato.pessoa.nome,
               'idPartido': candidato.partido.id_partido,
               'partido': candidato.partido.sigla}
    if candidato.vice is not None:
        retorno['vice'] = candidato.vice.pessoa.nome
        retorno['partidoVice'] = candidato.vice.partido.sigla
    return retorno


def votar(user, id_eleicao, votos):
    valida_credenciais(votos['usuario'], votos['senha'], user.eleitor.id_eleitor, votos['pin'])
    id_eleitor = c.enc(user.eleitor.id_eleitor)
    valida_voto(user, id_eleicao)
    id_cidade = user.eleitor.id_cidade
    for voto in votos['votos']:
        voto_enc = mf.cria_voto_encriptado(voto, id_cidade, id_eleitor)
        db.create(voto_enc)
    criar_hash_voto(user, votos['votos'])
    db.commit()


def valida_voto(user, id_eleicao):
    id_turno = valida_usuario_votou_e_retorna_turno_aberto(id_eleicao, user)
    turno = db.find_turno(id_turno)
    user.eleitor.turnos.append(turno)


def valida_credenciais(usuario, senha, id_eleitor, pin):
    login = db.find_login(usuario, senha)
    if login is None:
        msg = 'Credenciais incorretas'
        raise ValidationException(msg, [msg])
    pin_valido = db.busca_pin_valido_por_id_eleitor(id_eleitor)
    if pin_valido is None or pin_valido != pin:
        raise ValidationException('Credenciais incorretas', ['Credenciais incorretas'])


def gerar_pin(user):
    if db.busca_pin_valido_por_id_eleitor(user.eleitor.id_eleitor) is None:
        pin = PinEleitor()
        pin.id_eleitor = user.eleitor.id_eleitor
        user_pin = senha_util.generate_pin()
        pin.pin = senha_util.encrypt_md5(user_pin)
        db.create(pin)
        db.commit()

        email_util.enviar_email(user.email, f'Seu código para votar é: {user_pin} \n'
                                            f'O código é válido por apenas 5 minutos!')


def criar_hash_voto(user, votos):
    hash_voto = ''
    for voto in votos:
        hash_voto += voto['idCandidato']
    hash_voto = senha_util.encrypt_md5(hash_voto)
    email_util.enviar_email(user.email, f'Voto computado com sucesso às {str(datetime.now())}!'
                                        f'\nA sua hash é: {hash_voto}')
