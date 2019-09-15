package br.com.svo.web.eleicao.web.bean;

import br.com.svo.entities.Coligacao;
import br.com.svo.entities.Eleicao;
import br.com.svo.service.eleicao.EleicaoServiceLocal;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named("coligacaoWebBean")
public class ColigacaoWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    private List<Coligacao> coligacoes;

    @Inject
    private EleicaoWebBean eleicaoWebBean;

    @Inject
    private EleicaoServiceLocal eleicaoService;

    @PostConstruct
    private void init() {
        Eleicao eleicao = eleicaoWebBean.getEleicao();
        this.coligacoes = eleicaoService.buscarColigacoes(eleicao.getIdEleicao());
    }

//    GETTERS E SETTERS

    public List<Coligacao> getColigacoes() {
        return coligacoes;
    }

    public void setColigacoes(List<Coligacao> coligacoes) {
        this.coligacoes = coligacoes;
    }
}
