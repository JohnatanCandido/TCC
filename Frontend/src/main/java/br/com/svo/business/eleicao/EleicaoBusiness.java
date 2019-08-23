package br.com.svo.business.eleicao;

import br.com.svo.entities.Cargo;
import br.com.svo.entities.enums.SistemaEleicao;
import br.com.svo.entities.enums.TipoCargo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EleicaoBusiness implements Serializable {

    public static final long serialVersionUID = 1L;

    String[] nomeCargos = new String[] {"Presidente", "Governador", "Deputado Federal", "Senador", "Deputado Estadual"};
    TipoCargo[] tipos = new TipoCargo[] {TipoCargo.FEDERAL, TipoCargo.ESTADUAL, TipoCargo.FEDERAL, TipoCargo.FEDERAL, TipoCargo.ESTADUAL};

//    FIXME placeholder
    public List<Cargo> consultaCargos() {
        List<Cargo> cargos = new ArrayList<>();
        for (long i = 0; i < 5; i++) {
            Cargo cargo = new Cargo();
            cargo.setIdCargo(i);
            cargo.setNome(nomeCargos[(int) i]);
            cargo.setTipoCargo(tipos[(int) i]);
            cargo.setSistemaEleicao(SistemaEleicao.MAIORIA_SIMPLES);
            cargos.add(cargo);
        }
        return cargos;
    }
}
