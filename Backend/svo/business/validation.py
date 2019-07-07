from svo.entities.models import Eleicao, Cargo, Partido, Candidato, Eleitor, Login, VotoEncriptado
from svo.exception.validation_exception import ValidationException


def validar(entidade):
    errors = []
    if type(entidade) == Candidato:
        validar_candidato(entidade, errors)
    elif type(entidade) == Cargo:
        validar_cargo(entidade, errors)
    elif type(entidade) == Eleicao:
        validar_eleicao(entidade, errors)
    elif type(entidade) == Eleitor:
        validar_eleitor(entidade, errors)
    elif type(entidade) == Login:
        validar_login(entidade, errors)
    elif type(entidade) == Partido:
        validar_partido(entidade, errors)
    elif type(entidade) == VotoEncriptado:
        validar_voto(entidade, errors)

    if len(errors) > 0:
        raise ValidationException('Erros de validação', errors)


def validar_candidato(candidato, errors):
    if candidato.id_eleicao is None:
        errors.append("A eleição é obrigatória")
    if candidato.id_partido is None:
        errors.append("O partido é obrigatório")
    if candidato.id_cargo is None:
        errors.append("O cargo é obrigatório")
    if candidato.nome is None:
        errors.append("O nome é obrigatório")
    if candidato.numero is None:
        errors.append("O número do candidato é obrigatório")


def validar_cargo(cargo, errors):
    if cargo.nome is None:
        errors.append("O nome do cargo é obrigatório")
    if cargo.numero_representantes is None:
        errors.append("O número de representantes é obrigatório")
    if cargo.sistema_eleicao is None:
        errors.append("O sistema de eleição é obrigatório")


def validar_eleicao(eleicao, errors):
    if eleicao.titulo is None:
        errors.append("O título é obrigatório")
    if eleicao.inicio is None:
        errors.append("A data de início é obrigatório")
    if eleicao.termino is None:
        errors.append("A data de término é obrigatória")


def validar_eleitor(eleitor, errors):
    if eleitor.nome is None:
        errors.append("O nome é obrigatório")
    if eleitor.zona is None:
        errors.append("A zona eleitoral é obrigatória")
    if eleitor.numero_inscricao is None:
        errors.append("O número da inscrição é obrigatório")


def validar_login(login, errors):
    if login.usuario is None:
        errors.append("O nome de usuário é obrigatório")
    if login.senha is None:
        errors.append("A senha é obrigatória")


def validar_partido(partido, errors):
    if partido.numero_partido is None:
        errors.append("O número do partido é obrigatório")
    if partido.sigla is None:
        errors.append("A sigla do partido é obrigatória")
    if partido.nome is None:
        errors.append("O nome do partido é obrigatório")


def validar_voto(voto, errors):
    if voto.id_candidato is None:
        errors.append("O campo candidato é obrigatório")
    if voto.id_eleitor is None:
        errors.append("O campo eleitor é obrigatório")
    if voto.id_eleicao is None:
        errors.append("O campo eleição é obrigatório")
    if voto.id_cargo is None:
        errors.append("O campo cargo é obrigatório")
