package br.com.svo.service.eleicao;

import br.com.svo.business.eleicao.EleicaoBusiness;
import br.com.svo.entities.Cargo;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class EleicaoService implements EleicaoServiceLocal, Serializable {

    public static final long serialVersionUID = 1L;

    @Inject
    private EleicaoBusiness eleicaoBusiness;

    public List<Cargo> consultaCargos() {
        return eleicaoBusiness.consultaCargos();
    }
}
