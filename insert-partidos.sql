/*
SELECT * FROM partido
 */

START TRANSACTION;

INSERT INTO partido(nome, sigla, numero_partido) VALUES('Partido Social Liberal', 'PSL', '17');
INSERT INTO partido(nome, sigla, numero_partido) VALUES('Partido Renovador Trabalhista Brasileiro', 'PRTB', '28');
INSERT INTO partido(nome, sigla, numero_partido) VALUES('Partido dos Trabalhadores', 'PT', '13');
INSERT INTO partido(nome, sigla, numero_partido) VALUES('Partido Comunista do Brasil', 'PCdoB', '65');
INSERT INTO partido(nome, sigla, numero_partido) VALUES('PODEMOS', 'PODE', '19');
INSERT INTO partido(nome, sigla, numero_partido) VALUES('Partido Social Cristão', 'PSC', '20');
INSERT INTO partido(nome, sigla, numero_partido) VALUES('Patriota', 'PATRI', '51');
INSERT INTO partido(nome, sigla, numero_partido) VALUES('Partido Democrático Trabalhista', 'PDT', '12');
INSERT INTO partido(nome, sigla, numero_partido) VALUES('Partido da Social Democracia Brasileira', 'PSDB', '45');
INSERT INTO partido(nome, sigla, numero_partido) VALUES('Progressistas', 'PP', '11');
INSERT INTO partido(nome, sigla, numero_partido) VALUES('Partido Socialismo e Liberdade', 'PSOL', '50');
INSERT INTO partido(nome, sigla, numero_partido) VALUES('Partido Social Democrático', 'PSD', '55');
INSERT INTO partido(nome, sigla, numero_partido) VALUES('Democratas ', 'DEM', '25');
INSERT INTO partido(nome, sigla, numero_partido) VALUES('Movimento Democrático Brasileiro', 'MDB', '15');
INSERT INTO partido(nome, sigla, numero_partido) VALUES('Partido Comunista Brasileiro', 'PCB', '21');
INSERT INTO partido(nome, sigla, numero_partido) VALUES('Rede Sustentabilidade', 'REDE', '18');
INSERT INTO partido(nome, sigla, numero_partido) VALUES('Partido Socialista dos Trabalhadores Unificado', 'PSTU', '16');
INSERT INTO partido(nome, sigla, numero_partido) VALUES('Partido Republicano Brasileiro', 'PRB', '10');

COMMIT;