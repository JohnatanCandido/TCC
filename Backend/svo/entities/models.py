from svo import db


class Estado(db.Model):
    id_estado = db.Column(db.Integer, primary_key=True)
    nome = db.Column(db.String(100), nullable=False)
    cidades = db.relationship('Cidade', backref='estado', lazy=True)
    turno_cargo_regioes = db.relationship('TurnoCargoRegiao', backref='estado', lazy=True)

    def __repr__(self):
        return f'idEstado: {self.id_estado}, nome: {self.nome}'

    def to_json(self):
        return {
            'idEstado': self.id_estado,
            'nome': self.nome
        }


class Cidade(db.Model):
    id_cidade = db.Column(db.Integer, primary_key=True)
    id_estado = db.Column(db.Integer, db.ForeignKey('estado.id_estado'), nullable=False)
    nome = db.Column(db.String(100), nullable=False)
    turno_cargo_regioes = db.relationship('TurnoCargoRegiao', backref='cidade', lazy=True)
    eleitores = db.relationship('Eleitor', backref='cidade', lazy=True)
    votos_encriptados = db.relationship('VotoEncriptado', backref='cidade', lazy=True)
    votos_apurados = db.relationship('VotoApurado', backref='cidade', lazy=True)

    def __repr__(self):
        return f'idCidade: {self.id_cidade}, nome: {self.nome}'

    def to_json(self):
        return {
            'idCidade': self.id_cidade,
            'nome': self.nome,
            'estado': self.estado.to_json()
        }


eleitor_turno = db.Table('eleitor_turno', db.Model.metadata,
                         db.Column('id_eleitor', db.Integer, db.ForeignKey('eleitor.id_eleitor')),
                         db.Column('id_turno', db.Integer, db.ForeignKey('turno.id_turno')))


class Eleicao(db.Model):
    id_eleicao = db.Column(db.Integer, primary_key=True)
    titulo = db.Column(db.String(50), nullable=False, unique=True)
    observacao = db.Column(db.Text)
    turnos = db.relationship('Turno', backref='eleicao', lazy=True)

    def __repr__(self):
        return f'idEleicao: {self.id_eleicao}, título: {self.titulo}'

    def to_json(self):
        return {
            'idEleicao': self.id_eleicao,
            'titulo': self.titulo,
            'observacao': self.observacao,
            'turnos': [t.to_json() for t in self.turnos]
        }

    def turno_by_id(self, id_turno):
        turnos = [t for t in self.turnos if t.id_turno == id_turno]
        return turnos[0] if len(turnos) == 1 else Turno()


class Turno(db.Model):
    id_turno = db.Column(db.Integer, primary_key=True)
    id_eleicao = db.Column(db.Integer, db.ForeignKey('eleicao.id_eleicao'), nullable=False)
    turno = db.Column(db.Integer, nullable=False)
    inicio = db.Column(db.DateTime, nullable=False)
    termino = db.Column(db.DateTime, nullable=False)
    turnosCargos = db.relationship('TurnoCargo', backref='turno', lazy=True)

    def __repr__(self):
        return f'idTurno: {self.id_turno}, idEleicao: {self.id_eleicao}, início: {self.inicio}, término: {self.termino}'

    def turno_cargo_by_id(self, id_turno_cargo):
        tcs = [tc for tc in self.turnosCargos if tc.id_turno_cargo == id_turno_cargo]
        return tcs[0] if len(tcs) == 1 else TurnoCargo()

    def to_json(self):
        return {
            'idTurno': self.id_turno,
            'turno': self.turno,
            'inicio': str(self.inicio),
            'termino': str(self.termino),
            'turnoCargos': [tc.to_json() for tc in self.turnosCargos]
        }


class TipoCargo(db.Model):
    id_tipo_cargo = db.Column(db.Integer, primary_key=True)
    nome = db.Column(db.String(20), nullable=False, unique=True)
    cargos = db.relationship('Cargo', backref='tipoCargo', lazy=True)

    def __repr__(self):
        return f'idTipoCargo: {self.id_tipo_cargo}, nome: {self.nome}'

    def to_json(self):
        return {
            'idTipoCargo': self.id_tipo_cargo,
            'nome': self.nome
        }


class Cargo(db.Model):
    id_cargo = db.Column(db.Integer, primary_key=True)
    id_tipo_cargo = db.Column(db.Integer, db.ForeignKey('tipo_cargo.id_tipo_cargo'), nullable=False)
    nome = db.Column(db.String(20), nullable=False, unique=True)
    sistema_eleicao = db.Column(db.String(30), nullable=False)
    permite_segundo_turno = db.Column(db.Boolean, nullable=False)
    turnosCargos = db.relationship('TurnoCargo', backref='cargo', lazy=True)

    def __repr__(self):
        return f'idCargo: {self.id_cargo}, nome: {self.nome}'

    def to_json(self):
        return {
            'idCargo': self.id_cargo,
            'nome': self.nome,
            'sistemaEleicao': self.sistema_eleicao,
            'permiteSegundoTurno': self.permite_segundo_turno,
            'tipoCargo': self.tipoCargo.nome
        }


class TurnoCargo(db.Model):
    id_turno_cargo = db.Column(db.Integer, primary_key=True)
    id_turno = db.Column(db.Integer, db.ForeignKey('turno.id_turno'), nullable=False)
    id_cargo = db.Column(db.Integer, db.ForeignKey('cargo.id_cargo'), nullable=False)
    turno_cargo_regioes = db.relationship('TurnoCargoRegiao', backref='turnoCargo')

    def __repr__(self):
        return f'idTurnoCargo: {self.id_turno_cargo}'

    def to_json(self):
        return {
            'idTurnoCargo': self.id_turno_cargo,
            'idTurno': self.id_turno,
            'cargo': self.cargo.to_json(),
            'turnoCargoRegioes': [tcr.to_json() for tcr in self.turno_cargo_regioes]
        }

    def turno_cargo_regiao_by_id(self, id_turno_cargo_regiao):
        regioes = [tcr for tcr in self.turno_cargo_regioes if tcr.id_turno_cargo_regiao == id_turno_cargo_regiao]
        return regioes[0] if len(regioes) == 1 else TurnoCargoRegiao()


class TurnoCargoRegiao(db.Model):
    id_turno_cargo_regiao = db.Column(db.Integer, primary_key=True)
    id_turno_cargo = db.Column(db.Integer, db.ForeignKey('turno_cargo.id_turno_cargo'), nullable=False)
    qtd_cadeiras = db.Column(db.Integer, nullable=False)
    possui_segundo_turno = db.Column(db.Boolean, nullable=False)
    id_cidade = db.Column(db.Integer, db.ForeignKey('cidade.id_cidade'))
    id_estado = db.Column(db.Integer, db.ForeignKey('estado.id_estado'))
    votosEncriptados = db.relationship('VotoEncriptado', backref='turnoCargoRegiao', lazy=True)
    votosApurados = db.relationship('VotoApurado', backref='turnoCargoRegiao', lazy=True)
    candidatos = db.relationship('Candidato', backref='turnoCargoRegiao', lazy=True)

    def __repr__(self):
        return f'idTurnoCargoRegiao: {self.id_turno_cargo_regiao}'

    def to_json(self):
        json = {
            'idTurnoCargoRegiao': self.id_turno_cargo_regiao,
            'qtdCadeiras': self.qtd_cadeiras,
            'possuiSegundoTurno': self.possui_segundo_turno,
        }

        if self.cidade is not None:
            json['cidade'] = self.cidade.to_json()
        if self.estado is not None:
            json['estado'] = self.estado.to_json()
        return json

    __table_args__ = (
        db.UniqueConstraint('id_turno_cargo', 'id_cidade', 'id_estado', name='unique_turno_cargo_regiao'),
    )


class Partido(db.Model):
    id_partido = db.Column(db.Integer, primary_key=True)
    nome = db.Column(db.String(50), nullable=False, unique=True)
    sigla = db.Column(db.String(10), nullable=False, unique=True)
    numero_partido = db.Column(db.Integer, nullable=False, unique=True)
    candidatos = db.relationship('Candidato', backref='partido', lazy=True)

    def __repr__(self):
        return f'idPartido: {self.id_partido}, nome: {self.nome}, sigla: {self.sigla}'

    def to_json(self):
        return {
            'idPartido': self.id_partido,
            'nome': self.nome,
            'sigla': self.sigla,
            'numeroPartido': self.id_partido
        }


class Pessoa(db.Model):
    id_pessoa = db.Column(db.Integer, primary_key=True)
    nome = db.Column(db.String(100), nullable=False)
    cpf = db.Column(db.String(11), nullable=False)
    candidatos = db.relationship('Candidato', backref='pessoa', lazy=True)
    eleitor = db.relationship('Eleitor', backref='pessoa', uselist=False)
    login = db.relationship('Login', backref='pessoa', lazy=True, uselist=False)

    def __repr__(self):
        return f'idPessoa: {self.id_pessoa}, nome: {self.nome}'

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
    id_turno_cargo_regiao = db.Column(db.Integer, db.ForeignKey('turno_cargo_regiao.id_turno_cargo_regiao'), nullable=False)
    id_candidato_principal = db.Column(db.Integer, db.ForeignKey('candidato.id_candidato'))
    id_pessoa = db.Column(db.Integer, db.ForeignKey('pessoa.id_pessoa'), nullable=False)
    vice = db.relationship('Candidato', backref=db.backref('candidato_principal', remote_side='Candidato.id_candidato', uselist=False), uselist=False)
    votos = db.relationship('VotoApurado', backref='candidato', lazy=True)

    def __repr__(self):
        return f'idCandidato: {self.id_candidato}, número: {self.numero}'

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
    zona_eleitoral = db.Column(db.Integer, nullable=False)
    secao = db.Column(db.Integer, nullable=False)
    numero_inscricao = db.Column(db.Integer, nullable=False, unique=True)
    id_cidade = db.Column(db.Integer, db.ForeignKey('cidade.id_cidade'), nullable=False)
    turno = db.relationship('Turno', secondary=eleitor_turno)

    def __repr__(self):
        return f'idEleitor: {self.id_eleitor}, número inscrição: {self.numero_inscricao}'

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

    def __repr__(self):
        return f'idLogin: {self.id_login}, usuário: {self.usuario}'

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

    def __repr__(self):
        return f'idPerfil: {self.id_perfil}, nome: {self.nome}'

    def to_json(self):
        return {
            'idPerfil': self.id_perfil,
            'nome': self.nome
        }


class VotoEncriptado(db.Model):
    id_voto_encriptado = db.Column(db.Integer, primary_key=True)
    id_turno_cargo_regiao = db.Column(db.Integer, db.ForeignKey('turno_cargo_regiao.id_turno_cargo_regiao'), nullable=False)
    id_cidade = db.Column(db.Integer, db.ForeignKey('cidade.id_cidade'), nullable=False)
    id_candidato = db.Column(db.Text, nullable=False)
    id_eleitor = db.Column(db.Text, nullable=False)


class VotoApurado(db.Model):
    id_voto_apurado = db.Column(db.Integer, primary_key=True)
    id_turno_cargo_regiao = db.Column(db.Integer, db.ForeignKey('turno_cargo_regiao.id_turno_cargo_regiao'), nullable=False)
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