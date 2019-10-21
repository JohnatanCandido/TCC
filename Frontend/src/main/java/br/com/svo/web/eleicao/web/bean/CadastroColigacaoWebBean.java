package br.com.svo.web.eleicao.web.bean;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.business.exception.NoResultException;
import br.com.svo.entities.Coligacao;
import br.com.svo.entities.Partido;
import br.com.svo.service.eleicao.EleicaoServiceLocal;
import br.com.svo.util.SvoMessages;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.PrimeFaces;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named("cadastroColigacaoWebBean")
public class CadastroColigacaoWebBean implements Serializable {

    private Coligacao coligacao;
    private List<Coligacao> coligacoes;

    private Partido partido;
    private List<Partido> partidos = new ArrayList<>();

    @Inject
    private EleicaoServiceLocal eleicaoService;

    public void novaColigacao(Long idEleicao, List<Coligacao> coligacoes) {
        this.coligacao = new Coligacao(idEleicao);
        this.coligacoes = coligacoes;
    }

    public void editar(Coligacao coligacao) {
        this.coligacao = coligacao;
        coligacoes = null;
    }

    public void salvar() throws NoResultException {
        try {
            coligacao.setIdColigacao(eleicaoService.salvarColigacao(coligacao));
            if (coligacoes != null)
                coligacoes.add(coligacao);
            SvoMessages.addMessage("Salvo com sucesso");
            PrimeFaces.current().executeScript("PF('modalNovaColigacao').hide();");
        } catch (BusinessException e) {
            SvoMessages.addErrorMessage(e);
        }
    }

    public List<Partido> consultaPartidos(String filtro) throws NoResultException {
        try {
            partidos = eleicaoService.consultaPartidos(filtro);
        } catch (BusinessException e) {
            partidos = new ArrayList<>();
        }
        return partidos;
    }

    public void adicionarPartido() {
        coligacao.getPartidos().add(partido);
        partidos.remove(partido);
        partido = null;
    }


//    GETTERS E SETTERS

    public Coligacao getColigacao() {
        return coligacao;
    }

    public void setColigacao(Coligacao coligacao) {
        this.coligacao = coligacao;
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }
}
