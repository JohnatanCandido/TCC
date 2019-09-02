from svo.exception.validation_exception import ValidationException
from svo.util import database_utils as db
from svo.business import model_factory as mf


def pessoa_by_id(id_pessoa):
    pessoa = db.find_pessoa(id_pessoa)
    if pessoa is None:
        return None
    return pessoa.to_json()


def salvar_pessoa(dados):
    pessoa = mf.cria_pessoa(dados)
    validar_pessoa(pessoa)
    if pessoa.id_pessoa is None:
        db.create(pessoa)
    db.commit()


def validar_pessoa(pessoa):
    errors = []
    if pessoa.nome is None or pessoa.nome == '':
        errors.append('O nome é obrigatório')
    if pessoa.cpf is None or pessoa.cpf == '':
        errors.append('O CPF é obrigatório')
    if pessoa.login.usuario is None:
        errors.append('O usuário é obrigatório')
    else:
        validar_eleitor(pessoa.eleitor, errors)

    if errors:
        raise ValidationException('Erros de validação', errors)


def validar_eleitor(eleitor, errors):
    if eleitor.zona_eleitoral is None or eleitor.zona_eleitoral == '':
        errors.append('A zona eleitoral é obrigatória')
    if eleitor.numero_inscricao is None or eleitor.numero_inscricao == '':
        errors.append('O número de inscrição é obrigatório')
    if eleitor.secao is None or eleitor.secao == '':
        errors.append('A seção é obrigatória')
