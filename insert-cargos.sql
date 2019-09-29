/*
drop database svo;
create database svo;

select c.nome, tc.nome from cargo c
join tipo_cargo tc using(id_tipo_cargo)
 */

START TRANSACTION;
 
INSERT INTO tipo_cargo(nome) VALUES('Federal');
INSERT INTO tipo_cargo(nome) VALUES('Estadual');
INSERT INTO tipo_cargo(nome) VALUES('Municipal');

INSERT INTO cargo(nome, sistema_eleicao, permite_segundo_turno, id_tipo_cargo, max_votos, tam_numero_candidato) VALUES('Presidente', 'Maioria Simples', TRUE, currval('tipo_cargo_id_tipo_cargo_seq')-2, 1, 2);
INSERT INTO cargo(nome, sistema_eleicao, permite_segundo_turno, id_tipo_cargo, max_votos, tam_numero_candidato) VALUES('Governador', 'Maioria Simples', TRUE, currval('tipo_cargo_id_tipo_cargo_seq')-1, 1, 2);
INSERT INTO cargo(nome, sistema_eleicao, permite_segundo_turno, id_tipo_cargo, max_votos, tam_numero_candidato) VALUES('Prefeito', 'Maioria Simples', TRUE, currval('tipo_cargo_id_tipo_cargo_seq'), 1, 2);

INSERT INTO cargo(nome, sistema_eleicao, permite_segundo_turno, id_tipo_cargo, max_votos, tam_numero_candidato) VALUES('Deputado Federal', 'Representação Proporcional', FALSE, currval('tipo_cargo_id_tipo_cargo_seq')-2, 1, 4);
INSERT INTO cargo(nome, sistema_eleicao, permite_segundo_turno, id_tipo_cargo, max_votos, tam_numero_candidato) VALUES('Senador', 'Maioria Simples', FALSE, currval('tipo_cargo_id_tipo_cargo_seq')-2, 2, 3);
INSERT INTO cargo(nome, sistema_eleicao, permite_segundo_turno, id_tipo_cargo, max_votos, tam_numero_candidato) VALUES('Deputado Estadual', 'Representação Proporcional', FALSE, currval('tipo_cargo_id_tipo_cargo_seq')-1, 1, 5);
INSERT INTO cargo(nome, sistema_eleicao, permite_segundo_turno, id_tipo_cargo, max_votos, tam_numero_candidato) VALUES('Vereador', 'Representação Proporcional', FALSE, currval('tipo_cargo_id_tipo_cargo_seq'), 1, 5);

COMMIT;