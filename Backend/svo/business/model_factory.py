from svo.entities.models import Eleicao, Turno, TurnoCargo, TurnoCargoRegiao, Partido, Candidato, Eleitor, Login, VotoEncriptado
from svo.util import database_utils as db


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


# ====================================================================================================================

def cria_partido(dados):
    partido = Partido()
    partido.numero_partido = int(dados['partido'])
    partido.sigla = dados['sigla']
    partido.nome = dados['nome']
    return partido


def cria_candidato(dados):
    candidato = Candidato()
    candidato.nome = dados['nome']
    candidato.numero = int(dados['numero'])
    candidato.id_partido = int(dados['idPartido'])
    candidato.id_cargo = int(dados['idCargo'])
    candidato.id_eleicao = int(dados['idEleicao'])
    return candidato


def cria_eleitor(dados):
    eleitor = Eleitor()
    eleitor.nome = dados['nome']
    eleitor.zona = int(dados['zona'])
    eleitor.numero_inscricao = int(dados['numeroInscricao'])
    return eleitor


def cria_login(dados):
    login = Login()
    login.usuario = dados['usuario']
    login.senha = dados['senha']
    return login


def cria_voto_encriptado(dados):
    voto = VotoEncriptado()
    voto.id_candidato = str(dados['idCandidato'])
    voto.id_eleicao = str(dados['idEleitor'])
    voto.id_eleitor = str(dados['idEleicao'])
    voto.id_cargo = str(dados['idCargo'])
    return voto