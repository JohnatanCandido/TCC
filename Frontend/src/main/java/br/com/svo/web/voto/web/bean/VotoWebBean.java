package br.com.svo.web.voto.web.bean;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Voto;
import br.com.svo.entities.dto.CandidatoDTO;
import br.com.svo.service.votacao.VotacaoServiceLocal;
import br.com.svo.util.RedirectUtils;
import br.com.svo.util.SvoMessages;
import org.omnifaces.cdi.Param;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named("votoWebBean")
public class VotoWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    @Inject
    @Param(name = "idEleicao")
    private Long idEleicao;

    @Inject
    private VotacaoServiceLocal votacaoService;

    private List<Voto> votos = new ArrayList<>();

    private List<String> cargosNulos = new ArrayList<>();

    @PostConstruct
    public void init() {
        if (idEleicao == null)
            RedirectUtils.redirectToHome();
        try {
            votos = votacaoService.consultaCargos(idEleicao);
        } catch (BusinessException e) {
            SvoMessages.addFlashGlobalError(e);
            RedirectUtils.redirectToHome();
        }
    }

    public String getNomeCargo(Voto voto) {
        long qtVotos = votos.stream().filter(v -> v.getNomeCargo().equals(voto.getNomeCargo())).count();
        if (qtVotos == 1)
            return voto.getNomeCargo();
        return voto.getNomeCargo() + " " + voto.getIndex_voto();
    }

    public void buscaCandidato(Voto voto) {
        try {
            if (voto.getNumero() != null && voto.getNumero().toString().length() > 1) {
                CandidatoDTO candidato = votacaoService.consultaCandidato(voto.getIdTurnoCargoRegiao(), voto.getNumero());
                voto.selecionaCandidato(candidato);
            } else if (voto.getNumero() == null)
                voto.limpaCampos();
        } catch (BusinessException e) {
            voto.limpaCampos();
        }
    }

    public void votar() {
        if (validaVoto()) {
            confirmaVoto();
        } else {
            PrimeFaces.current().executeScript("PF('modalConfirmaVoto').show();");
        }
    }

    private boolean validaVoto() {
        cargosNulos.clear();
        for (Voto voto: votos) {
            if (voto.getIdCandidato() == null && voto.getIdPartido() == null)
                cargosNulos.add(getNomeCargo(voto));
        }
        return cargosNulos.isEmpty();
    }

    public void confirmaVoto() {
        try {
            votacaoService.votar(idEleicao, votos);
            SvoMessages.addMessage("Voto computado com sucesso!");
            RedirectUtils.redirectToHome();
        } catch (BusinessException e) {
            SvoMessages.addErrorMessage(e);
        }
    }

//    GETTERS E SETTERS

    public Long getIdEleicao() {
        return idEleicao;
    }

    public void setIdEleicao(Long idEleicao) {
        this.idEleicao = idEleicao;
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }

    public List<String> getCargosNulos() {
        return cargosNulos;
    }

    public void setCargosNulos(List<String> cargosNulos) {
        this.cargosNulos = cargosNulos;
    }
}
