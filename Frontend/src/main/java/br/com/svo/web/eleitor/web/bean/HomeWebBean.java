package br.com.svo.web.eleitor.web.bean;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.dto.EleicaoConsultaDTO;
import br.com.svo.service.eleicao.EleicaoServiceLocal;
import br.com.svo.util.SvoMessages;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Named("homeWebBean")
public class HomeWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    @Inject
    private EleicaoServiceLocal eleicaoService;

    private List<EleicaoConsultaDTO> eleicoesAbertas;
    private List<EleicaoConsultaDTO> eleicoesFinalizadas;

    @PostConstruct
    public void init() {
        try {
            List<EleicaoConsultaDTO> eleicoes = eleicaoService.consultaEleicoesUsuario();
            eleicoesAbertas = eleicoes.stream().filter(EleicaoConsultaDTO::isAberta).collect(Collectors.toList());
            eleicoesFinalizadas = eleicoes.stream().filter(e -> !e.isAberta()).collect(Collectors.toList());
        } catch (BusinessException e) {
            SvoMessages.addErrorMessage(e);
            eleicoesAbertas = new ArrayList<>();
            eleicoesFinalizadas = new ArrayList<>();
        }
    }

//    GETTERS E SETTERS

    public List<EleicaoConsultaDTO> getEleicoesAbertas() {
        return eleicoesAbertas;
    }

    public void setEleicoesAbertas(List<EleicaoConsultaDTO> eleicoesAbertas) {
        this.eleicoesAbertas = eleicoesAbertas;
    }

    public List<EleicaoConsultaDTO> getEleicoesFinalizadas() {
        return eleicoesFinalizadas;
    }

    public void setEleicoesFinalizadas(List<EleicaoConsultaDTO> eleicoesFinalizadas) {
        this.eleicoesFinalizadas = eleicoesFinalizadas;
    }
}
