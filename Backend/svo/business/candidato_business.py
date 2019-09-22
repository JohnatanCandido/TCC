from svo.business import model_factory as mf
from svo.entities.models import Candidato, TurnoCargoRegiao, TurnoCargo, Turno
from svo.exception.validation_exception import ValidationException
from svo.util import database_utils as db


def cadastrar(candidato_json):
    candidato = mf.cria_candidato(candidato_json)
    validar_candidato(candidato)
    verifica_pessoa_ja_candidatada(candidato.id_pessoa,
                                   int(candidato_json['idEleicao']),
                                   candidato_json['pessoa']['nome'])
    if candidato.id_candidato is None:
        db.create(candidato)
    if 'viceCandidato' in candidato_json:
        vice = mf.cria_candidato(candidato_json['viceCandidato'])
        vice.id_turno_cargo_regiao = candidato.id_turno_cargo_regiao
        candidato.vice = vice
        validar_candidato(vice)
        verifica_pessoa_ja_candidatada(vice.id_pessoa,
                                       int(candidato_json['idEleicao']),
                                       candidato_json['viceCandidato']['pessoa']['nome'])
        if vice.id_candidato is None:
            db.create(vice)
    db.commit()


def validar_candidato(candidato):
    errors = []
    if candidato.id_partido is None:
        errors.append("O partido é obrigatório")
    if candidato.id_turno_cargo_regiao is None:
        errors.append("O cargo é obrigatório")
    if candidato.id_pessoa is None:
        errors.append("É necessário selecionar uma pessoa")
    valida_numero_candidato(candidato, errors)

    if errors:
        raise ValidationException('Erros de validação', errors)


def valida_numero_candidato(candidato, errors):
    if candidato.numero is None:
        errors.append("O número do candidato é obrigatório")
    else:
        tcr = db.find_turno_cargo_regiao(candidato.id_turno_cargo_regiao)
        tamanho_numero = tcr.turnoCargo.cargo.tam_numero_candidato
        if len(str(candidato.numero)) != tamanho_numero:
            errors.append(f"O número do candiato deve ter {tamanho_numero} algarismos")
        if candidato.id_partido is not None:
            partido = db.find_partido(candidato.id_partido)
            if str(candidato.numero)[:2] != str(partido.numero_partido):
                errors.append(f"Os dois primeiros dígitos do número do candidato "
                              f"devem ser iguais ao do partido ({partido.numero_partido})")


def verifica_pessoa_ja_candidatada(id_pessoa, id_eleicao, nome):
    eleicoes = db.query(Candidato)\
                 .join(Candidato.turnoCargoRegiao)\
                 .join(TurnoCargoRegiao.turnoCargo)\
                 .join(TurnoCargo.turno)\
                 .filter(Candidato.id_pessoa == id_pessoa)\
                 .filter(Turno.id_eleicao == id_eleicao)\
                 .all()
    if eleicoes:
        raise ValidationException(f'{nome} já se candidatou nesta eleição',
                                  [f'{nome} já se candidatou nesta eleição'])


# noinspection PyComparisonWithNone
def busca_candidatos(id_turno_cargo_regiao):
    candidatos = db.query(Candidato)\
                   .join(Candidato.turnoCargoRegiao)\
                   .join(TurnoCargoRegiao.turnoCargo)\
                   .join(TurnoCargo.turno)\
                   .filter(Turno.turno == 1)\
                   .filter(Candidato.id_turno_cargo_regiao == id_turno_cargo_regiao)\
                   .filter(Candidato.id_candidato_principal == None)\
                   .all()
    if not candidatos:
        return []
    return [c.to_json() for c in candidatos]
