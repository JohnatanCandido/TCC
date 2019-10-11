/*
SELECT p.nome, p.cpf, e.zona_eleitoral, e.secao, e.numero_inscricao, c.nome, l.usuario, l.senha FROM pessoa p
JOIN login l using(id_pessoa)
join eleitor e using(id_pessoa)
join cidade c using(id_cidade)
 */

START TRANSACTION;

INSERT INTO pessoa(nome, cpf) VALUES('Johnatan Espíndola Cândido', '11404390901');
INSERT INTO login(usuario, senha, id_pessoa) VALUES(md5('johnatan.candido'), md5('teste'), currval('pessoa_id_pessoa_seq'));
INSERT INTO perfil(nome) VALUES('Administrador');
INSERT INTO login_perfil(id_login, id_perfil) VALUES(currval('login_id_login_seq'), currval('perfil_id_perfil_seq'));
INSERT INTO eleitor(id_pessoa, zona_eleitoral, secao, numero_inscricao, id_cidade) VALUES(currval('pessoa_id_pessoa_seq'), '073', '0136', '058788380973', 1);

-- Candidatos Presidente ===================================================================================================================================================
INSERT INTO pessoa(nome, cpf) VALUES('Jair Messia Bolsonaro', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('General Mourão', substr((random() * 1000000000000)::text, 0, 12));

INSERT INTO pessoa(nome, cpf) VALUES('Fernando Haddad', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('Manuela D''Avila', substr((random() * 1000000000000)::text, 0, 12));

INSERT INTO pessoa(nome, cpf) VALUES('Alvaro Dias', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('Paulo Rabello', substr((random() * 1000000000000)::text, 0, 12));

INSERT INTO pessoa(nome, cpf) VALUES('Cabo Daciolo', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('Suelene Balduino', substr((random() * 1000000000000)::text, 0, 12));

INSERT INTO pessoa(nome, cpf) VALUES('Ciro Gomes', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('Kátia Abreu', substr((random() * 1000000000000)::text, 0, 12));

INSERT INTO pessoa(nome, cpf) VALUES('Geraldo Alckmin', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('Ana Amélia Lemos', substr((random() * 1000000000000)::text, 0, 12));

INSERT INTO pessoa(nome, cpf) VALUES('Guilherme Boulos', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('Sônia Guajajara', substr((random() * 1000000000000)::text, 0, 12));

-- Candidatos Governador SC ================================================================================================================================================
INSERT INTO pessoa(nome, cpf) VALUES('Comandante Moisés', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('Daniela Reinehr', substr((random() * 1000000000000)::text, 0, 12));

INSERT INTO pessoa(nome, cpf) VALUES('Gelson Merisio', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('João Paulo Kleinübing', substr((random() * 1000000000000)::text, 0, 12));

INSERT INTO pessoa(nome, cpf) VALUES('Mauro Mariani', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('Napoleão Bernardes', substr((random() * 1000000000000)::text, 0, 12));

INSERT INTO pessoa(nome, cpf) VALUES('Décio Lima', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('Alcimar Oliveira', substr((random() * 1000000000000)::text, 0, 12));

INSERT INTO pessoa(nome, cpf) VALUES('Leonel Camasão', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('Caroline Bellaguarda', substr((random() * 1000000000000)::text, 0, 12));

INSERT INTO pessoa(nome, cpf) VALUES('Rogério Portanova', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('Regina Santos', substr((random() * 1000000000000)::text, 0, 12));

INSERT INTO pessoa(nome, cpf) VALUES('Jessé Pereira', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('Danny César Jumes', substr((random() * 1000000000000)::text, 0, 12));

INSERT INTO pessoa(nome, cpf) VALUES('Ingrid Assis', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('Ederson da Silva', substr((random() * 1000000000000)::text, 0, 12));


-- Candidatos Deputado Federal SC ==========================================================================================================================================
INSERT INTO pessoa(nome, cpf) VALUES('Hélio Costa', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('Daniel Freitas', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('Professor Pedro Uczai', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('Caroline de Toni', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('Geovania de Sa', substr((random() * 1000000000000)::text, 0, 12));
INSERT INTO pessoa(nome, cpf) VALUES('Carlos Chiodini', substr((random() * 1000000000000)::text, 0, 12));

INSERT INTO eleitor(id_pessoa, zona_eleitoral, secao, numero_inscricao, id_cidade)
(SELECT id_pessoa,
        substr((random() * 1000)::text, 0, 4),
        substr((random() * 10000)::text, 0, 5),
        substr((random() * 1000000000000)::text, 0, 13),
        1
 FROM pessoa p
WHERE NOT EXISTS((SELECT 1 FROM eleitor e2 WHERE p.id_pessoa = e2.id_pessoa)));

INSERT INTO login(usuario, senha, id_pessoa)
(SELECT md5(e.numero_inscricao), md5('teste'), e.id_pessoa FROM eleitor e WHERE NOT EXISTS(SELECT 1 FROM login l2 WHERE e.id_pessoa = l2.id_pessoa));

COMMIT;