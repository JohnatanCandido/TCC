package br.com.svo.web.eleicao.web.bean;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.business.exception.NoResultException;
import br.com.svo.entities.*;
import br.com.svo.service.eleicao.EleicaoServiceLocal;
import br.com.svo.util.SvoMessages;
import br.com.svo.util.Perfis;
import br.com.svo.util.RedirectUtils;
import org.omnifaces.cdi.Param;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Comparator;
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
                if (!identity.hasPerfil(Perfis.ADMINISTRADOR)) {
                    RedirectUtils.redirect("eleicao/busca/buscar-eleicao.html");
                    return;
                }
                eleicao = new Eleicao();
            } else {
                eleicao = eleicaoService.buscaEleicao(idEleicao);
            }
            cargosDisponiveis = eleicaoService.consultaCargos();
            cargosDisponiveis.removeIf(cargo -> eleicao.getTurnos().get(0).contemCargo(cargo));
        } catch (BusinessException e) {
            SvoMessages.addErrorMessage(e);
        } catch (NoResultException e) {
            SvoMessages.addFlashGlobalError("Eleição não encontrada");
            RedirectUtils.redirectToHome();
        }
    }

    public void adicionarCargo(Turno turno) {
        if (cargoSelecionado != null) {
            turno.getTurnoCargos().add(new TurnoCargo(turno.getIdTurno(), cargoSelecionado));
            cargosDisponiveis.remove(cargoSelecionado);
            cargoSelecionado = null;
        } else {
            SvoMessages.addErrorMessage("Selecione um cargo.");
        }
    }

    public void removerCargo(Turno turno, TurnoCargo turnoCargo) {
        turno.getTurnoCargos().remove(turnoCargo);
        cargosDisponiveis.add(turnoCargo.getCargo());
    }

    public boolean desabilitaModalCargoRegiao(TurnoCargo turnoCargo) {
        return turnoCargo.getCargo().getNome().equals("Presidente");
    }

    public void salvar() throws NoResultException {
        try {
            Long idEleicao = eleicaoService.salvar(eleicao);
            if (eleicao.getIdEleicao() == null) {
                RedirectUtils.redirect("eleicao/eleicao.html?idEleicao=" + idEleicao);
                SvoMessages.addFlash("Salvo com sucesso.");
            } else {
                SvoMessages.addMessage("Salvo com sucesso.");
            }
        } catch (BusinessException e) {
            SvoMessages.addErrorMessage(e);
        }
    }

    public void confirmar() {
        try {
            eleicaoService.confirmarEleicao(eleicao.getIdEleicao());
            eleicao.setConfirmada(true);
            SvoMessages.addMessage("Eleição confirmada com sucesso!");
        } catch (BusinessException e) {
            SvoMessages.addErrorMessage(e);
        }
    }

    public void apurar() {
        try {
            Long idTurno = eleicao.getTurnos().stream().max(Comparator.comparing(Turno::getTurno)).get().getIdTurno();
            String msg = eleicaoService.apurarTurno(idTurno);
            SvoMessages.addMessage(msg);
            eleicao.getTurnos().get(eleicao.getTurnos().size()-1).setSituacao("Em apuração");
        } catch (BusinessException e) {
            SvoMessages.addErrorMessage(e);
        }
    }

    public void recontagemVotos(int turno) {
        try {
            Long idTurno = eleicao.getTurnos().get(turno).getIdTurno();
            String msg = eleicaoService.recontagemVotos(idTurno);
            SvoMessages.addMessage(msg);
            eleicao.getTurnos().get(turno).setSituacao("Em apuração");
        } catch (BusinessException e) {
            SvoMessages.addErrorMessage(e);
        }
    }

    public boolean isPossuiPermissaoAdministrador() {
        return identity.hasPerfil(Perfis.ADMINISTRADOR);
    }

    public boolean isEleicaoPersistida() {
        return eleicao.getIdEleicao() != null;
    }

    public boolean isRenderizaAbasPermissaoAdministrador() {
        return isEleicaoPersistida() && isPossuiPermissaoAdministrador();
    }

    public boolean isRenderizarBotaoApurar() {
        return eleicao.getTurnos().stream().anyMatch(t -> "Aguardando apuração".equals(t.getSituacao()));
    }

    public boolean isPermiteAlteracoesSegundoTurno() {
        return eleicao.getTurnos().size() == 2 && eleicao.getTurnos().get(1).getSituacao().equals("Aguardando lançamento");
    }

    public boolean isPermiteAlterarDatasTurno(Turno turno) {
        return turno.getSituacao().equals("Aguardando lançamento");
    }

    public boolean isPermiteRecontarPrimeiroTurno() {
        return eleicao.getTurnos().get(0).getSituacao().equals("Apurado");
    }

    public boolean isPermiteRecontarSegundoTurno() {
        return eleicao.getTurnos().size() == 2 && eleicao.getTurnos().get(1).getSituacao().equals("Apurado");
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
