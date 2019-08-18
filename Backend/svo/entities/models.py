from svo import db


class Estado(db.Model):
    id_estado = db.Column(db.Integer, primary_key=True)
    nome = db.Column(db.String(100), nullable=False)
    cidades = db.relationship('Cidade', backref='estado', lazy=True)
    eleicoes = db.relationship('Eleicao', backref='estado', lazy=True)

    def to_json(self):
        return {
            'idEstado': self.id_estado,
            'nome': self.nome
        }


class Cidade(db.Model):
    id_cidade = db.Column(db.Integer, primary_key=True)
    id_estado = db.Column(db.Integer, db.ForeignKey('estado.id_estado'), nullable=False)
    nome = db.Column(db.String(100), nullable=False)
    eleicoes = db.relationship('Eleicao', backref='cidade', lazy=True)

    def to_json(self):
        return {
            'idCidade': self.id_cidade,
            'nome': self.nome
        }


class TipoEleicao(db.Model):
    id_tipo_eleicao = db.Column(db.Integer, primary_key=True)
    nome = db.Column(db.String(50), nullable=False, unique=True)
    eleicoes = db.relationship('Eleicao', backref='tipo', lazy=True)

    def to_json(self):
        return {
            'idTipoEleicao': self.id_tipo_eleicao,
            'nome': self.nome
        }


eleitor_eleicao = db.Table('eleitor_eleicao', db.Model.metadata,
                           db.Column('id_eleitor', db.Integer, db.ForeignKey('eleitor.id_eleitor')),
                           db.Column('id_eleicao', db.Integer, db.ForeignKey('eleicao.id_eleicao')))


class Eleicao(db.Model):
    id_eleicao = db.Column(db.Integer, primary_key=True)
    titulo = db.Column(db.String(50), nullable=False, unique=True)
    observacao = db.Column(db.Text)
    inicio = db.Column(db.DateTime, nullable=False)
    termino = db.Column(db.DateTime, nullable=False)
    eleicoesCargos = db.relationship('EleicaoCargo', backref='eleicao', lazy=True)
    id_cidade = db.Column(db.Integer, db.ForeignKey('cidade.id_cidade'))
    id_estado = db.Column(db.Integer, db.ForeignKey('estado.id_estado'))
    id_tipo_eleicao = db.Column(db.Integer, db.ForeignKey('tipo_eleicao.id_tipo_eleicao'), nullable=False)

    def to_json(self):
        return {
            'idEleicao': self.id_eleicao,
            'titulo': self.titulo,
            'observacao': self.observacao,
            'inicio': self.inicio,
            'termino': self.termino
        }


class Cargo(db.Model):
    id_cargo = db.Column(db.Integer, primary_key=True)
    nome = db.Column(db.String(20), nullable=False, unique=True)
    sistema_eleicao = db.Column(db.String(20), nullable=False)
    eleicoesCargos = db.relationship('EleicaoCargo', backref='cargo', lazy=True)

    def to_json(self):
        return {
            'id_cargo': self.id_cargo,
            'nome': self.nome,
            'sistemaEleicao': self.sistema_eleicao
        }


class EleicaoCargo(db.Model):
    id_eleicao_cargo = db.Column(db.Integer, primary_key=True)
    id_eleicao = db.Column(db.Integer, db.ForeignKey('eleicao.id_eleicao'), nullable=False)
    id_cargo = db.Column(db.Integer, db.ForeignKey('cargo.id_cargo'), nullable=False)
    qtd_cadeiras = db.Column(db.Integer, nullable=False, unique=True)
    candidatos = db.relationship('Candidato', backref='eleicaoCargo', lazy=True)

    __table_args__ = (
        db.UniqueConstraint('id_eleicao', 'id_cargo', name='unique_eleicao_cargo'),
    )

    def to_json(self):
        return {
            'idEleicao': self.id_eleicao,
            'idCargo': self.id_cargo,
            'qtd_cadeiras': self.qtd_cadeiras
        }


class Partido(db.Model):
    id_partido = db.Column(db.Integer, primary_key=True)
    numero_partido = db.Column(db.Integer, nullable=False, unique=True)
    sigla = db.Column(db.String(10), nullable=False, unique=True)
    nome = db.Column(db.String(50), nullable=False, unique=True)
    candidatos = db.relationship('Candidato', backref='partido', lazy=True)

    def to_json(self):
        return {
            'idPartido': self.id_partido,
            'numeroPartido': self.id_partido,
            'sigla': self.sigla,
            'nome': self.nome
        }


class Pessoa(db.Model):
    id_pessoa = db.Column(db.Integer, primary_key=True)
    nome = db.Column(db.String(100), nullable=False)
    cpf = db.Column(db.String(11), nullable=False)
    candidatos = db.relationship('Candidato', backref='pessoa', lazy=True)
    eleitor = db.relationship('Eleitor', backref='pessoa', uselist=False)
    login = db.relationship('Login', backref='pessoa', lazy=True, uselist=False)

    def to_json(self):
        return {
            'idPessoa': self.id_pessoa,
            'nome': self.nome,
            'cpf': self.cpf
        }


class Candidato(db.Model):
    id_candidato = db.Column(db.Integer, primary_key=True)
    numero = db.Column(db.Integer, nullable=False)
    id_partido = db.Column(db.Integer, db.ForeignKey('partido.id_partido'), nullable=False)
    id_eleicao_cargo = db.Column(db.Integer, db.ForeignKey('eleicao_cargo.id_eleicao_cargo'), nullable=False)
    id_candidato_principal = db.Column(db.Integer, db.ForeignKey('candidato.id_candidato'))
    id_pessoa = db.Column(db.Integer, db.ForeignKey('pessoa.id_pessoa'), nullable=False)
    vice = db.relationship('Candidato', backref=db.backref('candidato_principal', remote_side='Candidato.id_candidato', uselist=False), uselist=False)
    votos = db.relationship('VotoApurado', backref='candidato', lazy=True)

    def to_json(self):
        return {
            'idCandidato': self.id_candidato,
            'numero': self.numero,
            'idPartido': self.id_partido,
            'idCargo': self.id_cargo,
            'idEleicao': self.id_eleicao
        }


class Eleitor(db.Model):
    id_eleitor = db.Column(db.Integer, primary_key=True)
    id_pessoa = db.Column(db.Integer, db.ForeignKey('pessoa.id_pessoa'), nullable=False)
    id_cidade = db.Column(db.Integer, db.ForeignKey('cidade.id_cidade'), nullable=False)
    zona_eleitoral = db.Column(db.Integer, nullable=False)
    numero_inscricao = db.Column(db.Integer, nullable=False, unique=True)
    eleicoes = db.relationship('Eleicao', secondary=eleitor_eleicao)

    def to_json(self):
        return {
            'idEleitor': self.id_eleitor,
            'zonaEleitoral': self.zona_eleitoral,
            'numeroInscricao': self.numero_inscricao
        }


login_perfil = db.Table('login_perfil', db.Model.metadata,
                        db.Column('id_login', db.Integer, db.ForeignKey('login.id_login')),
                        db.Column('id_perfil', db.Integer, db.ForeignKey('perfil.id_perfil')))


class Login(db.Model):
    id_login = db.Column(db.Integer, primary_key=True)
    usuario = db.Column(db.String(50), nullable=False, unique=True)
    senha = db.Column(db.String(100), nullable=False)
    id_pessoa = db.Column(db.Integer, db.ForeignKey('pessoa.id_pessoa'), nullable=False)
    perfis = db.relationship('Perfil', secondary=login_perfil)

    def has_perfil(self, perfil):
        return len([p for p in self.perfis if p.nome == perfil]) > 0

    def to_json(self):
        return {
            'pessoa': self.pessoa.to_json(),
            'perfis': [perfil.to_json() for perfil in self.perfis]
        }


class Perfil(db.Model):
    id_perfil = db.Column(db.Integer, primary_key=True)
    nome = db.Column(db.String(25), nullable=False, unique=True)

    def to_json(self):
        return {
            'idPerfil': self.id_perfil,
            'nome': self.nome
        }


class VotoEncriptado(db.Model):
    id_voto_encriptado = db.Column(db.Integer, primary_key=True)
    id_eleicao_cargo = db.Column(db.Integer, db.ForeignKey('eleicao_cargo.id_eleicao_cargo'), nullable=False)
    id_cidade = db.Column(db.Integer, db.ForeignKey('cidade.id_cidade'), nullable=False)
    id_candidato = db.Column(db.Text, nullable=False)
    id_eleitor = db.Column(db.Text, nullable=False)


class VotoApurado(db.Model):
    id_voto_apurado = db.Column(db.Integer, primary_key=True)
    id_eleicao_cargo = db.Column(db.Integer, db.ForeignKey('eleicao_cargo.id_eleicao_cargo'), nullable=False)
    id_cidade = db.Column(db.Integer, db.ForeignKey('cidade.id_cidade'))
    id_candidato = db.Column(db.Integer, db.ForeignKey('candidato.id_candidato'), nullable=False)
    id_eleitor = db.Column(db.Text, nullable=False)

    def to_json(self):
        return {
            'idCargo': self.id_cargo,
            'idEleicao': self.id_eleicao,
            'idCidade': self.id_cidade,
            'candidato': self.candidato,
            'idEleitor': self.id_eleitor
        }
