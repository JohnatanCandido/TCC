from svo.entities.models import Pessoa, Eleitor, Candidato
from svo.util import database_utils as db, senha_util
from svo.business import voto_business
from svo import c

from random import randint, shuffle


def cria_pessoas():
    for i in range(5000):
        cria_pessoa(i)
        if i % 5000 == 0:
            db.commit()
    db.commit()


def cria_pessoa(i):
    pessoa = Pessoa()
    pessoa.nome = f'Teste {i}'
    pessoa.cpf = numero_aleatorio(11)
    pessoa.email = f'teste{i}.teste@teste.com'
    eleitor = Eleitor()
    eleitor.id_cidade = 1
    eleitor.zona_eleitoral = numero_aleatorio(3)
    eleitor.secao = numero_aleatorio(4)
    eleitor.numero_inscricao = numero_aleatorio(12)
    pessoa.eleitor = eleitor
    db.create(pessoa)
    print(f'Criando pessoa {pessoa.nome}')


def numero_aleatorio(length):
    n = ''
    for i in range(length):
        n += str(randint(0, 9))
    return n


def votar(id_eleicao):
    sql = '''select e.id_eleitor as id_pessoa from pessoa p
             join eleitor e on p.id_pessoa = e.id_pessoa
             where p.nome like '%Teste%' 
             and not exists(select 1 from eleitor_turno et 
                            where e.id_eleitor = et.id_eleitor and et.id_turno = :idTurno)'''

    id_turno = voto_business.consulta_turno_aberto_por_eleicao(id_eleicao)
    result = db.native(sql, {'idTurno': id_turno}).fetchall()
    id_eleitores = [r[0] for r in result]
    # Thread(target=cria_votos, kwargs={'id_eleitores': id_eleitores[:1000], 'id_turno': id_turno}).start()
    cria_votos(id_eleitores, id_turno)


def cria_votos(id_eleitores, id_turno):
    for id_eleitor in id_eleitores:
        hash_voto = ''
        print(f'Voto de {id_eleitor}')
        cands = [1, 3, 5, 7]
        shuffle(cands)
        cand = db.query(Candidato).get(cands[0])
        id_candidato = c.enc(cand.id_candidato)
        id_partido = c.enc(cand.id_partido)
        id_eleitor_enc = c.enc(id_eleitor)
        id_turno_cargo_regiao = 1
        id_cidade = 1

        hash_voto += str(id_candidato)

        sql = 'INSERT INTO voto_encriptado(id_turno_cargo_regiao, id_cidade, id_candidato, id_partido, id_eleitor)' \
              'VALUES(:idTurnoCargoRegiao, :idCidade, :idCandidato, :idPartido, :idEleitor)'

        db.native(sql, {'idTurnoCargoRegiao': id_turno_cargo_regiao,
                        'idCidade': id_cidade,
                        'idCandidato': id_candidato,
                        'idPartido': id_partido,
                        'idEleitor': id_eleitor_enc})

        # candidatos = [1, 3, 5, 7]
        # shuffle(candidatos)
        # cand = db.query(Candidato).get(candidatos[0])
        # id_candidato = c.enc(cand.id_candidato)
        # id_partido = c.enc(cand.id_partido)
        # id_turno_cargo_regiao = 1
        # id_cidade = 1
        #
        # hash_voto += str(id_candidato)
        #
        # sql = 'INSERT INTO voto_encriptado(id_turno_cargo_regiao, id_cidade, id_candidato, id_partido, id_eleitor)' \
        #       'VALUES(:idTurnoCargoRegiao, :idCidade, :idCandidato, :idPartido, :idEleitor)'
        #
        # db.native(sql, {'idTurnoCargoRegiao': id_turno_cargo_regiao,
        #                 'idCidade': id_cidade,
        #                 'idCandidato': id_candidato,
        #                 'idPartido': id_partido,
        #                 'idEleitor': id_eleitor_enc})

        insert_eleitor_turno(id_eleitor, id_turno, senha_util.encrypt_md5(hash_voto))
        db.commit()


def insert_eleitor_turno(id_eleitor, id_turno, hash_voto):
    sql = 'INSERT INTO eleitor_turno(id_eleitor, id_turno, hash, hora_voto) VALUES(:idEleitor, :idTurno, :hash, now())'
    db.native(sql, {'idEleitor': id_eleitor, 'idTurno': id_turno, 'hash': hash_voto})


def cria_candidatos(id_tcr, qt):
    for i in range(qt):
        print(f'Criando candidato {i}')
        candidato = Candidato()
        candidato.id_pessoa = randint(12, 5002)
        partido = db.find_partido(randint(1, 18))
        candidato.numero = int(str(partido.numero_partido) + numero_aleatorio(2))
        candidato.id_partido = partido.id_partido
        candidato.id_turno_cargo_regiao = id_tcr
        db.create(candidato)
        if i % 500 == 0:
            print(f'Commitando')
            db.commit()
            print(f'Commitado')
    print(f'Commitando')
    db.commit()
    print(f'Commitado')
