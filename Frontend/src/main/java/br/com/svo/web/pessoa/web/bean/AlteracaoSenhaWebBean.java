package br.com.svo.web.pessoa.web.bean;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.dto.AlteracaoSenhaDTO;
import br.com.svo.service.pessoa.PessoaServiceLocal;
import br.com.svo.util.SvoMessages;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named("alteracaoSenhaWebBean")
public class AlteracaoSenhaWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    @Inject
    private PessoaServiceLocal pessoaService;

    private AlteracaoSenhaDTO alteracaoSenhaDTO;

    @PostConstruct
    public void init() {
        alteracaoSenhaDTO = new AlteracaoSenhaDTO();
    }

    public void salvarSenha() {
        try {
            pessoaService.salvarSenha(alteracaoSenhaDTO);
            SvoMessages.addMessage("Senha alterada com sucesso!");
        } catch (BusinessException e) {
            SvoMessages.addErrorMessage(e);
        }
    }

//    GETTERS E SETTERS

    public AlteracaoSenhaDTO getAlteracaoSenhaDTO() {
        return alteracaoSenhaDTO;
    }

    public void setAlteracaoSenhaDTO(AlteracaoSenhaDTO alteracaoSenhaDTO) {
        this.alteracaoSenhaDTO = alteracaoSenhaDTO;
    }
}
