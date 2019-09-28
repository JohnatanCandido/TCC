/*
drop database svo;
create database svo;

select * from pessoa
select * from eleitor
select * from login
select * from partido
select * from coligacao
select * from coligacao_partido
 */

insert into tipo_cargo(nome) values('Federal');
insert into tipo_cargo(nome) values('Estadual');
insert into tipo_cargo(nome) values('Municipal');

insert into cargo(nome, sistema_eleicao, permite_segundo_turno, id_tipo_cargo, max_votos, tam_numero_candidato) values('Presidente', 'Maioria Simples', true, 1, 1);
insert into cargo(nome, sistema_eleicao, permite_segundo_turno, id_tipo_cargo, max_votos, tam_numero_candidato) values('Governador', 'Maioria Simples', true, 1, 2);
insert into cargo(nome, sistema_eleicao, permite_segundo_turno, id_tipo_cargo, max_votos, tam_numero_candidato) values('Prefeito', 'Maioria Simples', true, 1, 3);

insert into cargo(nome, sistema_eleicao, permite_segundo_turno, id_tipo_cargo, max_votos, tam_numero_candidato) values('Deputado Federal', 'Representação Proporcional', false, 1, 4);
insert into cargo(nome, sistema_eleicao, permite_segundo_turno, id_tipo_cargo, max_votos, tam_numero_candidato) values('Senador', 'Representação Proporcional', false, 2, 3);
insert into cargo(nome, sistema_eleicao, permite_segundo_turno, id_tipo_cargo, max_votos, tam_numero_candidato) values('Deputado Estadual', 'Representação Proporcional', false, 1, 5);
insert into cargo(nome, sistema_eleicao, permite_segundo_turno, id_tipo_cargo, max_votos, tam_numero_candidato) values('Vereador', 'Representação Proporcional', false, 1, 5);

insert into estado(nome, sigla) values('Santa Catarina', 'SC');
insert into cidade(id_estado, nome) values(currval('estado_id_estado_seq'), 'Imbituba');
insert into cidade(id_estado, nome) values(currval('estado_id_estado_seq'), 'Tubarão');
insert into cidade(id_estado, nome) values(currval('estado_id_estado_seq'), 'Laguna');
insert into cidade(id_estado, nome) values(currval('estado_id_estado_seq'), 'Capivari de Baixo');
insert into cidade(id_estado, nome) values(currval('estado_id_estado_seq'), 'Florianópolis');

insert into pessoa(nome, cpf) values('Johnatan Espíndola Cândido', '11404390901');
insert into login(usuario, senha, id_pessoa) values('johnatan.candido', md5('teste'), currval('pessoa_id_pessoa_seq'));
insert into perfil(nome) values('Administrador');
insert into login_perfil(id_login, id_perfil) values(currval('login_id_login_seq'), currval('perfil_id_perfil_seq'));
insert into eleitor(id_pessoa, zona_eleitoral, secao, numero_inscricao, id_cidade) values(currval('pessoa_id_pessoa_seq'), '073', '0136', '058788380973', 1);

insert into pessoa(nome, cpf) values('Jair Messia Bolsonaro', substr((random() * 1000000000000)::text, 0, 12));
insert into pessoa(nome, cpf) values('General Mourão', substr((random() * 1000000000000)::text, 0, 12));
insert into pessoa(nome, cpf) values('Fernando Haddad', substr((random() * 1000000000000)::text, 0, 12));
insert into pessoa(nome, cpf) values('Manuela D''Avila', substr((random() * 1000000000000)::text, 0, 12));
insert into pessoa(nome, cpf) values('Alvaro Dias', substr((random() * 1000000000000)::text, 0, 12));
insert into pessoa(nome, cpf) values('Paulo Rabello', substr((random() * 1000000000000)::text, 0, 12));
insert into pessoa(nome, cpf) values('Cabo Daciolo', substr((random() * 1000000000000)::text, 0, 12));
insert into pessoa(nome, cpf) values('Suelene Balduino', substr((random() * 1000000000000)::text, 0, 12));
insert into pessoa(nome, cpf) values('Ciro Gomes', substr((random() * 1000000000000)::text, 0, 12));
insert into pessoa(nome, cpf) values('Kátia Abreu', substr((random() * 1000000000000)::text, 0, 12));
insert into pessoa(nome, cpf) values('Geraldo Alckmin', substr((random() * 1000000000000)::text, 0, 12));
insert into pessoa(nome, cpf) values('Ana Amélia Lemos', substr((random() * 1000000000000)::text, 0, 12));
insert into pessoa(nome, cpf) values('Guilherme Boulos', substr((random() * 1000000000000)::text, 0, 12));
insert into pessoa(nome, cpf) values('Sônia Guajajara', substr((random() * 1000000000000)::text, 0, 12));

insert into eleitor(id_pessoa, zona_eleitoral, secao, numero_inscricao, id_cidade)
(select id_pessoa,
        substr((random() * 1000)::text, 0, 4),
        substr((random() * 10000)::text, 0, 5),
        substr((random() * 1000000000000)::text, 0, 13),
        1
 from pessoa p
where not exists((select 1 from eleitor e2 where p.id_pessoa = e2.id_pessoa)));

insert into login(usuario, senha, id_pessoa)
(select md5(e.numero_inscricao), md5('teste'), e.id_pessoa from eleitor e where not exists(select 1 from login l2 where e.id_pessoa = l2.id_pessoa));

insert into partido(nome, sigla, numero_partido) values ('Partido Social Liberal', 'PSL', '17');
insert into partido(nome, sigla, numero_partido) values ('Partido Renovador Trabalhista Brasileiro', 'PRTB', '28');
insert into partido(nome, sigla, numero_partido) values ('Partido dos Trabalhadores', 'PT', '13');
insert into partido(nome, sigla, numero_partido) values ('Partido Comunista do Brasil', 'PCdoB', '65');
insert into partido(nome, sigla, numero_partido) values ('PODEMOS', 'PODE', '19');
insert into partido(nome, sigla, numero_partido) values ('Partido Social Cristão', 'PSC', '20');
insert into partido(nome, sigla, numero_partido) values ('Patriota', 'PATRI', '51');
insert into partido(nome, sigla, numero_partido) values ('Partido Democrático Trabalhista', 'PDT', '12');
insert into partido(nome, sigla, numero_partido) values ('Partido da Social Democracia Brasileira', 'PSDB', '45');
insert into partido(nome, sigla, numero_partido) values ('Progressistas', 'PP', '11');
insert into partido(nome, sigla, numero_partido) values ('Partido Socialismo e Liberdade', 'PSOL', '50');
