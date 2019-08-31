package br.com.svo.web.eleicao.web.bean;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.dto.EleicaoConsultaDTO;
import br.com.svo.service.eleicao.EleicaoServiceLocal;
import br.com.svo.util.Messages;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named("buscaEleicaoWebBean")
public class BuscaEleicaoWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    private EleicaoConsultaDTO eleicaoConsultaDTO;
    private List<EleicaoConsultaDTO> eleicoes;

    @Inject
    private EleicaoServiceLocal eleicaoService;

    @PostConstruct
    public void init() {
        this.eleicaoConsultaDTO = new EleicaoConsultaDTO();
        this.eleicoes = new ArrayList<>();
    }

    public void buscar() {
        try {
            eleicoes = eleicaoService.consultarEleicoes(eleicaoConsultaDTO);
            if (eleicoes.isEmpty())
                Messages.addErrorMessage("Nenhuma eleição encontrada!");
            else
                Messages.addFoundMessage(eleicoes.size());
        } catch (BusinessException e) {
            Messages.addErrorMessage(e);
        }
    }

//    GETTERS E SETTERS

    public EleicaoConsultaDTO getEleicaoConsultaDTO() {
        return eleicaoConsultaDTO;
    }

    public void setEleicaoConsultaDTO(EleicaoConsultaDTO eleicaoConsultaDTO) {
        this.eleicaoConsultaDTO = eleicaoConsultaDTO;
    }

    public List<EleicaoConsultaDTO> getEleicoes() {
        return eleicoes;
    }

    public void setEleicoes(List<EleicaoConsultaDTO> eleicoes) {
        this.eleicoes = eleicoes;
    }
}
