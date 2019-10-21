package br.com.svo.web.eleicao.web.bean;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.business.exception.NoResultException;
import br.com.svo.entities.Identity;
import br.com.svo.entities.dto.EleicaoConsultaDTO;
import br.com.svo.service.eleicao.EleicaoServiceLocal;
import br.com.svo.util.Perfis;
import br.com.svo.util.RedirectUtils;
import br.com.svo.util.SvoMessages;
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

    @Inject
    private Identity identity;

    @PostConstruct
    public void init() {
        this.eleicaoConsultaDTO = new EleicaoConsultaDTO();
        this.eleicoes = new ArrayList<>();
    }

    public void buscar() {
        try {
            eleicoes = eleicaoService.consultarEleicoes(eleicaoConsultaDTO);
            SvoMessages.addFoundMessage(eleicoes.size());
        } catch (BusinessException e) {
            SvoMessages.addErrorMessage(e);
        } catch (NoResultException e) {
            SvoMessages.addErrorMessage("Nenhuma eleição encontrada!");
        }
    }

    public boolean isRenderizaBotaoNovaEleicao() {
        return identity.hasPerfil(Perfis.ADMINISTRADOR);
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
