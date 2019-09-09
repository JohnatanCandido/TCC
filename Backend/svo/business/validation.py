from svo.entities.models import Eleicao, Cargo, Partido, Candidato, Eleitor, Login, VotoEncriptado
from svo.exception.validation_exception import ValidationException


def validar(entidade):
    errors = []
    if type(entidade) == Cargo:
        validar_cargo(entidade, errors)
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


def validar_cargo(cargo, errors):
    if cargo.nome is None:
        errors.append("O nome do cargo é obrigatório")
    if cargo.numero_representantes is None:
        errors.append("O número de representantes é obrigatório")
    if cargo.sistema_eleicao is None:
        errors.append("O sistema de eleição é obrigatório")


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
