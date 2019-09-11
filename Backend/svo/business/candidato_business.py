from svo.business import model_factory as mf
from svo.entities.models import Candidato, TurnoCargoRegiao, TurnoCargo, Turno, Partido
from svo.exception.validation_exception import ValidationException
from svo.util import database_utils as db


def cadastrar(candidato_json):
    candidato = mf.cria_candidato(candidato_json)
    verifica_pessoa_ja_candidatada(candidato.id_pessoa, int(candidato_json['idEleicao']))
    validar_candidato(candidato)
    if candidato.id_candidato is None:
        db.create(candidato)
    if 'viceCandidato' in candidato_json:
        vice = mf.cria_candidato(candidato_json['viceCandidato'])
        vice.id_candidato_principal = candidato.id_candidato
        verifica_pessoa_ja_candidatada(vice.id_pessoa, int(candidato_json['idEleicao']))
        validar_candidato(vice)
        if vice.id_candidato is None:
            db.create(vice)
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


def busca_partido(nome):
    partidos = db.query(Partido).filter(Partido.nome == nome).all()
    if not partidos:
        return []
    return [p.campos_consulta() for p in partidos]


def cadastrar_partido(dados):
    partido = mf.cria_partido(dados)
    validar_partido(partido)
    if partido.id_partido is None:
        db.create(partido)
    db.commit()


def validar_partido(partido):
    errors = []
    if partido.numero_partido is None:
        errors.append("O número do partido é obrigatório")
    if partido.sigla is None:
        errors.append("A sigla do partido é obrigatória")
    if partido.nome is None:
        errors.append("O nome do partido é obrigatório")
    if errors:
        raise ValidationException('Erros de validação', errors)


def busca_candidatos(id_turno_cargo_regiao):
    candidatos = db.query(Candidato).filter(Candidato.id_turno_cargo_regiao == id_turno_cargo_regiao).all()
    if not candidatos:
        return []
    return [c.to_json() for c in candidatos]
