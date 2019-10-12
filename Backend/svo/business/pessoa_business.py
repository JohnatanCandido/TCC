from svo.exception.validation_exception import ValidationException
from svo.util import database_utils as db, email_util
from svo.business import model_factory as mf

# Usado na consulta de pessoa
# noinspection PyUnresolvedReferences
from svo.entities.models import Pessoa, Eleitor, Cidade, Estado

# Usado na consulta de pessoa
# noinspection PyUnresolvedReferences
import re


def pessoa_by_id(id_pessoa):
    pessoa = db.find_pessoa(id_pessoa)
    if pessoa is None:
        return None
    return pessoa.to_json()


def salvar_pessoa(dados):
    pessoa, senha = mf.cria_pessoa(dados)
    validar_pessoa(pessoa)
    if pessoa.id_pessoa is None:
        db.create(pessoa)
    db.commit()

    if senha is not None:
        corpo_email = f'Parabéns! Agora você pode votar pela internet com as credenciais abaixo:.\n'\
                      f'Usuário: {pessoa.eleitor.numero_inscricao}\n'\
                      f'Senha: {senha}'
        email_util.enviar_email(pessoa.email, corpo_email)
    return str(pessoa.id_pessoa)


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


def consultar_pessoas(filtro):
    if 'idPessoa' in filtro:
        pessoa = db.find_pessoa(int(filtro['idPessoa']))
        if pessoa is None:
            return []
        return [pessoa.campos_consulta()]
    query = 'db.query(Pessoa)'
    if join_eleitor(filtro):
        query += '.join(Pessoa.eleitor)'
    if join_cidade(filtro):
        query += '.join(Eleitor.cidade)'
    if 'idEstado' in filtro:
        query += '.join(Cidade.estado)'
        query += '.filter(Estado.id_estado == filtro["idEstado"])'
    if 'nome' in filtro:
        query += '.filter(Pessoa.nome.ilike(f\'%{filtro["nome"]}%\'))'
    if 'cpf' in filtro:
        query += '.filter(Pessoa.cpf.ilike(f\'%{re.sub("[.-]", "", filtro["cpf"])}%\'))'
    if 'idCidade' in filtro:
        query += '.filter(Cidade.id_cidade == filtro["idCidade"])'
    if 'zonaEleitoral' in filtro:
        query += '.filter(Eleitor.zona_eleitoral == filtro["zonaEleitoral"])'
    if 'secao' in filtro:
        query += '.filter(Eleitor.secao == filtro["secao"])'
    if 'numeroInscricao' in filtro:
        query += '.filter(Eleitor.numero_inscricao == filtro["numeroInscricao"])'
    query += '.all()'
    pessoas = eval(query)
    if not pessoas:
        return []
    return [p.campos_consulta() for p in pessoas]


def join_eleitor(filtro):
    if 'idEstado' in filtro:
        return True
    if 'idCidade' in filtro:
        return True
    if 'zonaEleitoral' in filtro:
        return True
    if 'secao' in filtro:
        return True
    if 'numeroInscricao' in filtro:
        return True
    return False


def join_cidade(filtro):
    if 'idEstado' in filtro:
        return True
    if 'idCidade' in filtro:
        return True
    return False
