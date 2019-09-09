from svo.business import model_factory as mf
from svo.entities.models import Candidato, TurnoCargoRegiao, TurnoCargo, Turno
from svo.exception.validation_exception import ValidationException
from svo.util import database_utils as db


def cadastrar(candidato_json):
    candidato = mf.cria_candidato(candidato_json)
    verifica_pessoa_ja_candidatada(candidato.id_pessoa, int(candidato_json['idEleicao']))
    validar_candidato(candidato)
    if candidato.id_candidato is None:
        db.create(candidato)
    db.commit()


def validar_candidato(candidato):
    errors = []
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

    if errors:
        raise ValidationException('Erros de validação', errors)


def verifica_pessoa_ja_candidatada(id_pessoa, id_eleicao):
    eleicoes = db.query(Candidato)\
                 .join(Candidato.turnoCargoRegiao)\
                 .join(TurnoCargoRegiao.turnoCargo)\
                 .join(TurnoCargo.turno)\
                 .filter(Candidato.id_pessoa == id_pessoa)\
                 .filter(Turno.id_eleicao == id_eleicao)\
                 .all()
    if eleicoes:
        raise ValidationException('Esta pessoa já se candidatou nesta eleição',
                                  'Esta pessoa já se candidatou nesta eleição')
