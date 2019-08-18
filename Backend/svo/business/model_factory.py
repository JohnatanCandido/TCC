from svo.entities.models import Eleicao, Cargo, Partido, Candidato, Eleitor, Login, VotoEncriptado


def cria_eleicao(dados):
    eleicao = Eleicao()
    eleicao.titulo = dados['titulo']
    eleicao.observacao = dados['observacao']
    eleicao.inicio = dados['inicio']
    eleicao.termino = dados['termino']
    return eleicao


def cria_cargo(dados):
    cargo = Cargo()
    cargo.nome = dados['nome']
    cargo.numero_representantes = int(dados['numeroRepresentantes'])
    cargo.sistema_eleicao = dados['sistemaEleicao']
    return cargo


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