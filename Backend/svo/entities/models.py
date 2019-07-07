from svo import db


class Eleicao(db.Model):
    id_eleicao = db.Column(db.Integer, primary_key=True)
    titulo = db.Column(db.String(50), nullable=False, unique=True)
    observacao = db.Column(db.Text)
    inicio = db.Column(db.DateTime, nullable=False)
    termino = db.Column(db.DateTime, nullable=False)

    def to_json(self):
        return {'idEleicao': self.id_eleicao,
                'titulo': self.titulo,
                'observacao': self.observacao,
                'inicio': self.inicio,
                'termino': self.termino}


class Cargo(db.Model):
    id_cargo = db.Column(db.Integer, primary_key=True)
    nome = db.Column(db.String(20), nullable=False, unique=True)
    numero_representantes = db.Column(db.Integer, nullable=False)
    sistema_eleicao = db.Column(db.String(20), nullable=False)

    def to_json(self):
        return {'id_cargo': self.id_cargo,
                'nome': self.nome,
                'numeroRepresentantes': self.numero_representantes,
                'sistemaEleicao': self.sistema_eleicao}


class Partido(db.Model):
    id_partido = db.Column(db.Integer, primary_key=True)
    numero_partido = db.Column(db.Integer, nullable=False, unique=True)
    sigla = db.Column(db.String(10), nullable=False, unique=True)
    nome = db.Column(db.String(50), nullable=False, unique=True)

    def to_json(self):
        return {'idPartido': self.id_partido,
                'numeroPartido': self.id_partido,
                'sigla': self.sigla,
                'nome': self.nome}


class Candidato(db.Model):
    id_candidato = db.Column(db.Integer, primary_key=True)
    nome = db.Column(db.String(100), nullable=False)
    numero = db.Column(db.Integer, nullable=False)
    id_partido = db.Column(db.Integer, db.ForeignKey('partido.id_partido'), nullable=False)
    id_cargo = db.Column(db.Integer, db.ForeignKey('cargo.id_cargo'), nullable=False)
    id_eleicao = db.Column(db.Integer, db.ForeignKey('eleicao.id_eleicao'), nullable=False)

    def to_json(self):
        return {'idCandidato': self.id_candidato,
                'nome': self.nome,
                'numero': self.numero,
                'idPartido': self.id_partido,
                'idCargo': self.id_cargo,
                'idEleicao': self.id_eleicao}


class Eleitor(db.Model):
    id_eleitor = db.Column(db.Integer, primary_key=True)
    nome = db.Column(db.String(100), nullable=False)
    zona = db.Column(db.Integer, nullable=False)
    numero_inscricao = db.Column(db.Integer, nullable=False, unique=True)
    login = db.relationship('Login', backref='eleitor', lazy=True)

    def to_json(self):
        return {'idEleitor': self.id_eleitor,
                'nome': self.nome,
                'zona': self.zona,
                'numeroInscricao': self.numero_inscricao,
                'login': self.login.to_json()}


class Login(db.Model):
    id_login = db.Column(db.Integer, primary_key=True)
    usuario = db.Column(db.String(100), nullable=False, unique=True)
    senha = db.Column(db.String(100), nullable=False)
    id_eleitor = db.Column(db.Integer, db.ForeignKey('eleitor.id_eleitor'), nullable=False)

    def to_json(self):
        return {'idLogin': self.id_login,
                'usuario': self.usuario,
                'idEleitor': self.id_eleitor}


class VotoEncriptado(db.Model):
    id_voto_encriptado = db.Column(db.Integer, primary_key=True)
    id_candidato = db.Column(db.Text, nullable=False)
    id_eleitor = db.Column(db.Text, nullable=False)
    id_eleicao = db.Column(db.Text, nullable=False)
    id_cargo = db.Column(db.Text, nullable=False)

    def to_json(self):
        return {'idVotoEncriptado': self.id_voto_encriptado,
                'idCandidato': self.id_candidato,
                'idEleitor': self.id_eleitor,
                'idEleicao': self.id_eleicao,
                'idCargo': self.id_cargo}


class Voto(db.Model):
    id_voto = db.Column(db.Integer, primary_key=True)
    id_candidato = db.Column(db.Integer, db.ForeignKey('candidato.id_candidato'), nullable=False)
    id_eleicao = db.Column(db.Integer, db.ForeignKey('eleicao.id_eleicao'), nullable=False)
    id_cargo = db.Column(db.Integer, db.ForeignKey('cargo.id_cargo'), nullable=False)
    hash_eleitor = db.Column(db.Integer, nullable=False)

    def to_json(self):
        return {'idVoto': self.id_voto,
                'idCandidato': self.id_candidato,
                'idEleicao': self.id_eleicao,
                'idCargo': self.id_cargo,
                'hash_eleitor': self.hash_eleitor}
