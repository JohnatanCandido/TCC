/*
SELECT p.nome, p.cpf, e.zona_eleitoral, e.secao, e.numero_inscricao, c.nome, l.usuario, l.senha FROM pessoa p
JOIN login l using(id_pessoa)
join eleitor e using(id_pessoa)
join cidade c using(id_cidade)
 */

START TRANSACTION;

INSERT INTO pessoa(nome, cpf, email) VALUES('Johnatan Espíndola Cândido', '11404390901', 'johnatanespindola@gmail.com');
INSERT INTO login(usuario, senha, id_pessoa) VALUES(md5('admin'), md5('admin'), currval('pessoa_id_pessoa_seq'));
INSERT INTO perfil(nome) VALUES('Administrador');
INSERT INTO login_perfil(id_login, id_perfil) VALUES(currval('login_id_login_seq'), currval('perfil_id_perfil_seq'));
INSERT INTO eleitor(id_pessoa, zona_eleitoral, secao, numero_inscricao, id_cidade) VALUES(currval('pessoa_id_pessoa_seq'), '073', '0136', '058788380973', 1);


INSERT INTO eleitor(id_pessoa, zona_eleitoral, secao, numero_inscricao, id_cidade)
(SELECT id_pessoa,
        substr((random() * 10000)::text, 0, 4),
        substr((random() * 100000)::text, 0, 5),
        substr((random() * 10000000000000)::text, 0, 13),
        1
 FROM pessoa p
WHERE NOT EXISTS((SELECT 1 FROM eleitor e2 WHERE p.id_pessoa = e2.id_pessoa)));

INSERT INTO login(usuario, senha, id_pessoa)
(SELECT md5(e.numero_inscricao), md5('teste'), e.id_pessoa FROM eleitor e WHERE NOT EXISTS(SELECT 1 FROM login l2 WHERE e.id_pessoa = l2.id_pessoa));

COMMIT;