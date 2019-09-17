package br.com.svo.web.eleicao.web.bean;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Cargo;
import br.com.svo.entities.Eleicao;
import br.com.svo.entities.Identity;
import br.com.svo.entities.Turno;
import br.com.svo.entities.TurnoCargo;
import br.com.svo.service.eleicao.EleicaoServiceLocal;
import br.com.svo.util.Messages;
import br.com.svo.util.Perfis;
import br.com.svo.util.RedirectUtils;
import org.omnifaces.cdi.Param;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named("eleicaoWebBean")
public class EleicaoWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    @Inject
    @Param(name = "idEleicao")
    private Long idEleicao;

    @Inject
    private EleicaoServiceLocal eleicaoService;

    @Inject
    private Identity identity;

    private Eleicao eleicao;
    private List<Cargo> cargosDisponiveis;
    private Cargo cargoSelecionado;

    @PostConstruct
    public void init() {
        try {
            if (idEleicao == null) {
                eleicao = new Eleicao();
            } else {
                eleicao = eleicaoService.buscaEleicao(idEleicao);
            }
            cargosDisponiveis = eleicaoService.consultaCargos();
            cargosDisponiveis.removeIf(cargo -> eleicao.getTurnos().get(0).contemCargo(cargo));
        } catch (BusinessException e) {
            Messages.addErrorMessage(e);
        }
    }

    public void adicionarCargo(Turno turno) {
        if (cargoSelecionado != null) {
            turno.getTurnoCargos().add(new TurnoCargo(turno.getIdTurno(), cargoSelecionado));
            cargosDisponiveis.remove(cargoSelecionado);
            cargoSelecionado = null;
        } else {
            Messages.addErrorMessage("Selecione um cargo.");
        }
    }

    public void removerCargo(Turno turno, TurnoCargo turnoCargo) {
        turno.getTurnoCargos().remove(turnoCargo);
        cargosDisponiveis.add(turnoCargo.getCargo());
    }

    public boolean desabilitaModalCargoRegiao(TurnoCargo turnoCargo) {
        return turnoCargo.getCargo().getNome().equals("Presidente");
    }

    public void salvar() {
        try {
            Long idEleicao = eleicaoService.salvar(eleicao);
            if (eleicao.getIdEleicao() == null)
                RedirectUtils.redirect("eleicao/eleicao.html?idEleicao=" + idEleicao);
            Messages.addMessage("Salvo com sucesso.");
        } catch (BusinessException e) {
            Messages.addErrorMessage(e);
        }
    }

    public boolean isPossuiPermissaoAdministrador() {
        return identity.hasPerfil(Perfis.ADMINISTRADOR);
    }

    public boolean isRenderizarAbaVotar() {
        return eleicao.isAberta() && !eleicao.isUsuarioVotou();
    }

//    GETTERS E SETTERS

    public Eleicao getEleicao() {
        return eleicao;
    }

    public void setEleicao(Eleicao eleicao) {
        this.eleicao = eleicao;
    }

    public Long getIdEleicao() {
        return idEleicao;
    }

    public void setIdEleicao(Long idEleicao) {
        this.idEleicao = idEleicao;
    }

    public List<Cargo> getCargosDisponiveis() {
        return cargosDisponiveis;
    }

    public void setCargosDisponiveis(List<Cargo> cargosDisponiveis) {
        this.cargosDisponiveis = cargosDisponiveis;
    }

    public Cargo getCargoSelecionado() {
        return cargoSelecionado;
    }

    public void setCargoSelecionado(Cargo cargoSelecionado) {
        this.cargoSelecionado = cargoSelecionado;
    }

}
