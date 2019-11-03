CREATE OR REPLACE FUNCTION impede_update_e_delete() RETURNS TRIGGER AS $$
    BEGIN
        RAISE EXCEPTION 'Os dados desta tabela não podem ser modificados';
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION impede_insert_voto_encriptado_fora_de_periodo_eleicao() RETURNS TRIGGER AS $$
    BEGIN
        IF (SELECT TRUE FROM turno t
            JOIN turno_cargo tc ON t.id_turno = tc.id_turno
            JOIN turno_cargo_regiao tcr ON tc.id_turno_cargo = tcr.id_turno_cargo
            WHERE tcr.id_turno_cargo_regiao = NEW.id_turno_cargo_regiao
            AND now() BETWEEN t.inicio AND t.termino)
        THEN
            RETURN NEW;
        ELSE
            RAISE EXCEPTION 'Não se pode votar nesta eleição pois não há turno em andamento!';
       END IF;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION impede_insert_voto_apurado_fora_de_periodo_apuracao() RETURNS TRIGGER AS $$
    BEGIN
        IF (SELECT TRUE FROM turno t
            JOIN apuracao a ON t.id_turno = a.id_turno
            JOIN turno_cargo tc ON t.id_turno = tc.id_turno
            JOIN turno_cargo_regiao tcr ON tc.id_turno_cargo = tcr.id_turno_cargo
            WHERE tcr.id_turno_cargo_regiao = NEW.id_turno_cargo_regiao
            AND now() BETWEEN a.inicio_apuracao AND COALESCE(a.termino_apuracao, 'INFINITY'))
        THEN
            RETURN NEW;
        ELSE
            RAISE EXCEPTION 'Não é permitido inserir este dado pois não há apuração em andamento!';
       END IF;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER impede_modificacao_voto_encriptado
BEFORE UPDATE OR DELETE ON voto_encriptado
FOR EACH ROW EXECUTE PROCEDURE impede_update_e_delete();

CREATE TRIGGER impede_modificacao_voto_apurado
BEFORE UPDATE OR DELETE ON voto_apurado
FOR EACH ROW EXECUTE PROCEDURE impede_update_e_delete();

CREATE TRIGGER impede_insercao_voto_encriptado
BEFORE INSERT ON voto_encriptado
FOR EACH ROW EXECUTE PROCEDURE impede_insert_voto_encriptado_fora_de_periodo_eleicao();

CREATE TRIGGER impede_insercao_voto_apurado
BEFORE INSERT ON voto_apurado
FOR EACH ROW EXECUTE PROCEDURE impede_insert_voto_apurado_fora_de_periodo_apuracao();
