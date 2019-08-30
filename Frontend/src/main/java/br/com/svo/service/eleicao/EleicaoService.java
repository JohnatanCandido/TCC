package br.com.svo.service.eleicao;

import br.com.svo.business.eleicao.EleicaoBusiness;
import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Cargo;
import br.com.svo.entities.Eleicao;
import br.com.svo.entities.dto.EleicaoConsultaDTO;

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

    public void salvar(Eleicao eleicao) throws BusinessException {
        eleicaoBusiness.salvar(eleicao);
    }

    @Override
    public Eleicao buscaEleicao(Long idEleicao) throws BusinessException {
        return eleicaoBusiness.buscaEleicao(idEleicao);
    }

    @Override
    public List<EleicaoConsultaDTO> consultarEleicoes(EleicaoConsultaDTO filtro) throws BusinessException {
        return eleicaoBusiness.consultarEleicoes(filtro);
    }
}
