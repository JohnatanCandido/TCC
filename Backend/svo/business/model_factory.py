from svo.entities.models import Eleicao, Turno, TurnoCargo, TurnoCargoRegiao, Partido, Candidato, Eleitor, Login, \
    VotoEncriptado, Pessoa, Coligacao, VotoApurado
from svo.exception.validation_exception import ValidationException
from svo.util import database_utils as db, senha_util
from svo import c

import re


# Eleição ==============================================================================================================

def cria_eleicao(dados):
    if 'idEleicao' in dados:
        eleicao = db.find_eleicao(int(dados['idEleicao']))
    else:
        eleicao = Eleicao()

    eleicao.titulo = dados['titulo']
    eleicao.observacao = dados['observacao']
    for turnoDados in dados['turnos']:
        if 'idTurno' in turnoDados:
            turno = eleicao.turno_by_id(int(turnoDados['idTurno']))
            cria_turno(turnoDados, eleicao, turno)
        else:
            eleicao.turnos.append(cria_turno(turnoDados, eleicao, None))
    return eleicao


def cria_turno(dados, eleicao, turno):
    if turno is None:
        turno = Turno()
    turno.id_eleicao = eleicao.id_eleicao
    turno.turno = dados['turno']

    if 'inicio' in dados:
        turno.inicio = dados['inicio']
    if 'termino' in dados:
        turno.termino = dados['termino']
    if 'turnoCargos' in dados:
        remove_turno_cargos(dados['turnoCargos'], turno)
        for turnoCargoDados in dados['turnoCargos']:
            if 'idTurnoCargo' in turnoCargoDados:
                turno_cargo = turno.turno_cargo_by_id(int(turnoCargoDados['idTurnoCargo']))
                cria_turno_cargo(turnoCargoDados, turno, turno_cargo)
            else:
                turno.turnosCargos.append(cria_turno_cargo(turnoCargoDados, turno, None))
    return turno


def remove_turno_cargos(dados, turno):
    for turnoCargo in turno.turnosCargos:
        if len([t for t in dados if 'idTurnoCargo' in t and t['idTurnoCargo'] == turnoCargo.id_turno_cargo]) == 0:
            turno.turnosCargos.remove(turnoCargo)
            if turnoCargo.id_turno_cargo is not None:
                [db.delete(tcr) for tcr in turnoCargo.turno_cargo_regioes]
                db.delete(turnoCargo)


def cria_turno_cargo(dados, turno, turno_cargo):
    if turno_cargo is None:
        turno_cargo = TurnoCargo()
    cargo = db.find_cargo(dados['cargo']['idCargo'])
    turno_cargo.id_cargo = cargo.id_cargo
    turno_cargo.cargo = cargo
    turno_cargo.id_turno = turno.id_turno

    if 'turnoCargoRegioes' in dados:
        remove_turno_cargo_regiao(dados['turnoCargoRegioes'], turno_cargo)
        for turnoCargoRegiaoDados in dados['turnoCargoRegioes']:
            if 'idTurnoCargoRegiao' in turnoCargoRegiaoDados:
                turno_cargo_regiao = turno_cargo.turno_cargo_regiao_by_id(turnoCargoRegiaoDados['idTurnoCargoRegiao'])
                cria_turno_cargo_regiao(turnoCargoRegiaoDados, turno_cargo, turno_cargo_regiao)
            else:
                turno_cargo.turno_cargo_regioes.append(cria_turno_cargo_regiao(turnoCargoRegiaoDados, turno_cargo, None))
    return turno_cargo


def remove_turno_cargo_regiao(dados, turno_cargo):
    for turnoCargoRegiao in turno_cargo.turno_cargo_regioes:
        if len([tcr for tcr in dados if 'idTurnoCargoRegiao' in tcr and tcr['idTurnoCargoRegiao'] == turnoCargoRegiao.id_turno_cargo_regiao]) == 0:
            turno_cargo.turno_cargo_regioes.remove(turnoCargoRegiao)
            if turnoCargoRegiao.id_turno_cargo_regiao is not None:
                db.delete(turnoCargoRegiao)


def cria_turno_cargo_regiao(dados, turno_cargo, turno_cargo_regiao):
    if turno_cargo_regiao is None:
        turno_cargo_regiao = TurnoCargoRegiao()
    turno_cargo_regiao.id_turno_cargo = turno_cargo.id_turno_cargo
    turno_cargo_regiao.qtd_cadeiras = dados['qtdCadeiras']
    turno_cargo_regiao.possui_segundo_turno = dados['possuiSegundoTurno']

    if 'estado' in dados and dados['estado']['idEstado'] != turno_cargo_regiao.id_estado:
        estado = db.find_estado(dados['estado']['idEstado'])
        turno_cargo_regiao.id_estado = estado.id_estado
        turno_cargo_regiao.estado = estado
    if 'cidade' in dados and dados['cidade']['idCidade'] != turno_cargo_regiao.id_cidade:
        cidade = db.find_cidade(dados['cidade']['idCidade'])
        turno_cargo_regiao.id_cidade = cidade.id_cidade
        turno_cargo_regiao.cidade = cidade
    return turno_cargo_regiao


# Pessoa ===============================================================================================================

def cria_pessoa(dados):
    if 'idPessoa' in dados:
        pessoa = db.find_pessoa(int(dados['idPessoa']))
    else:
        pessoa = Pessoa()
    if 'nome' in dados:
        pessoa.nome = dados['nome']
    if 'cpf' in dados:
        pessoa.cpf = re.sub('[.-]', '', dados['cpf'])
    if 'eleitor' in dados:
        if pessoa.eleitor is not None:
            cria_eleitor(dados['eleitor'], pessoa.eleitor)
        else:
            pessoa.eleitor = cria_eleitor(dados['eleitor'], None)
    if pessoa.login is None:
        pessoa.login = Login()
        pessoa.login.usuario = pessoa.eleitor.numero_inscricao
        pessoa.login.senha = senha_util.generate_password()
    if 'perfis' in dados:
        pessoa.login.perfis.clear()
        for p in dados['perfis']:
            perfil = db.find_perfil(p['idPerfil'])
            pessoa.login.perfis.append(perfil)
    return pessoa


def cria_eleitor(dados, eleitor):
    if eleitor is None:
        eleitor = Eleitor()
    eleitor.zona_eleitoral = dados['zonaEleitoral']
    eleitor.numero_inscricao = re.sub(' ', '', dados['numeroInscricao'])
    eleitor.secao = dados['secao']

    if 'cidade' in dados:
        eleitor.id_cidade = dados['cidade']['idCidade']
    return eleitor


# Candidato ============================================================================================================

def cria_candidato(dados):
    candidato = Candidato()
    if 'idCandidato' in dados:
        candidato.id_candidato = int(dados['idCandidato'])
    if 'numero' in dados:
        candidato.numero = int(dados['numero'])
    if 'partido' in dados:
        candidato.id_partido = int(dados['partido']['idPartido'])
    if 'turnoCargoRegiao' in dados:
        candidato.id_turno_cargo_regiao = int(dados['turnoCargoRegiao']['idTurnoCargoRegiao'])
    if 'pessoa' in dados:
        candidato.id_pessoa = int(dados['pessoa']['idPessoa'])
    return candidato


# Partido ==============================================================================================================

def cria_partido(dados):
    partido = Partido()
    if 'idPartido' in dados:
        partido = db.find_partido(int(dados['idPartido']))
    if 'idColigacao' in dados:
        partido.coligacoes.append(db.find_coligacao(int(dados['idColigacao'])))
    partido.numero_partido = int(dados['numeroPartido'])
    partido.sigla = dados['sigla']
    partido.nome = dados['nome']
    return partido


def cria_coligacao(dados):
    coligacao = Coligacao()
    if 'idColigacao' in dados:
        coligacao = db.find_coligacao(int(dados['idColigacao']))
    if 'partidos' in dados:
        for partido in dados['partidos']:
            p = cria_partido(partido)
            if p not in coligacao.partidos:
                coligacao.partidos.append(p)
    coligacao.nome = dados['nome']
    coligacao.id_eleicao = int(dados['idEleicao'])
    return coligacao


# Voto =================================================================================================================

def cria_voto_encriptado(dados, id_cidade, id_eleitor):
    voto = VotoEncriptado()
    if 'idCandidato' in dados:
        voto.id_candidato = dados['idCandidato']
    if 'idPartido' in dados:
        voto.id_partido = dados['idPartido']
    if 'idCandidato' not in dados and 'idPartido' not in dados:
        msg = 'É necessário informar um candidato ou um partido'
        raise ValidationException(msg, [msg])
    voto.id_eleitor = id_eleitor
    voto.id_turno_cargo_regiao = dados['idTurnoCargoRegiao']
    voto.id_cidade = id_cidade

    return voto


def cria_voto(voto_enc):
    va = VotoApurado()
    va.id_turno_cargo_regiao = voto_enc.id_turno_cargo_regiao
    va.id_cidade = voto_enc.id_cidade
    va.id_eleitor = voto_enc.id_eleitor
    va.id_voto_encriptado = voto_enc.id_voto_encriptado

    id_candidato = c.dec(int(voto_enc.id_candidato))
    id_partido = c.dec(int(voto_enc.id_partido))
    if id_candidato != -1:
        va.id_candidato = id_candidato
    if id_partido != -1:
        va.id_partido = id_partido
    return va
