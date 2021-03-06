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
import java.util.List;

@ViewScoped
@Named("cadastroPartidoWebBean")
public class CadastroPartidoWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    private Partido partido;

    @Inject
    private EleicaoServiceLocal eleicaoService;

    public void novoPartido() {
        this.partido = new Partido();
    }

    public void salvar() throws NoResultException {
        try {
            eleicaoService.salvarPartido(partido);
            SvoMessages.addMessage("Salvo com sucesso");
            PrimeFaces.current().executeScript("PF('modalNovoPartido').hide();");
        } catch (BusinessException e) {
            SvoMessages.addErrorMessage(e);
        }
    }

//    GETTERS E SETTERS

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }
}
