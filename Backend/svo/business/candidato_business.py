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
    candidatos = db.native(query_busca_candidato(), {'idTcr': id_turno_cargo_regiao}).fetchall()
    if not candidatos:
        return []
    return [json_candidato(c) for c in candidatos]


def query_busca_candidato():
    return '''
        SELECT candidato.id_candidato,
               candidato.numero,
               pessoa.nome,
               partido.numero_partido,
               partido.nome,
               partido.sigla,
               pessoa_vice.nome,
               partido_vice.numero_partido,
               partido_vice.sigla,
               partido_vice.nome,
               CASE WHEN candidato.qt_votos != 0
                   THEN candidato.qt_votos
                   ELSE count(va.id_candidato) END,
               candidato.situacao,
               estado.sigla
        FROM candidato
        JOIN pessoa ON candidato.id_pessoa = pessoa.id_pessoa
        JOIN partido ON candidato.id_partido = partido.id_partido
        JOIN turno_cargo_regiao ON candidato.id_turno_cargo_regiao = turno_cargo_regiao.id_turno_cargo_regiao
        LEFT JOIN estado ON turno_cargo_regiao.id_estado = estado.id_estado
        LEFT JOIN voto_apurado va ON candidato.id_candidato = va.id_candidato
        LEFT JOIN candidato vice ON candidato.id_candidato = vice.id_candidato_principal
        LEFT JOIN pessoa pessoa_vice ON pessoa_vice.id_pessoa = vice.id_pessoa
        LEFT JOIN partido partido_vice ON vice.id_partido = partido_vice.id_partido
        WHERE candidato.id_candidato_principal IS NULL AND candidato.id_turno_cargo_regiao = :idTcr
        GROUP BY candidato.id_candidato,
                 candidato.numero,
                 pessoa.nome,
                 partido.nome,
                 partido.sigla,
                 partido.numero_partido,
                 candidato.situacao,
                 candidato.qt_votos,
                 pessoa_vice.nome,
                 partido_vice.nome,
                 partido_vice.sigla,
                 partido_vice.numero_partido,
                 estado.sigla
        ORDER BY count(va.id_candidato) DESC, pessoa.nome
    '''


def json_candidato(c):
    return {
        'idCandidato': c[0],
        'numero': c[1],
        'nome': c[2],
        'numeroPartido': c[3],
        'nomePartido': c[4],
        'siglaPartido': c[5],
        'nomeVice': c[6],
        'numeroPartidoVice': c[7],
        'siglaPartidoVice': c[8],
        'nomePartidoVice': c[9],
        'votos': c[10],
        'situacao': c[11],
        'estado': c[12]
    }
