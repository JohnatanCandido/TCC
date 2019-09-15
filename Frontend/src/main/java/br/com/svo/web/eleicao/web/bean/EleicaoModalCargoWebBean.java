package br.com.svo.web.eleicao.web.bean;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Cidade;
import br.com.svo.entities.Estado;
import br.com.svo.entities.TurnoCargo;
import br.com.svo.entities.TurnoCargoRegiao;
import br.com.svo.entities.enums.SistemaEleicao;
import br.com.svo.entities.enums.TipoCargo;
import br.com.svo.service.regiao.RegiaoServiceLocal;
import br.com.svo.util.Messages;
import org.omnifaces.cdi.ViewScoped;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named("eleicaoModalCargoWebBean")
public class EleicaoModalCargoWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    @Inject
    private RegiaoServiceLocal regiaoService;

    private TurnoCargo turnoCargo;

    private List<Estado> estados;
    private List<Cidade> cidades;

    private Estado estado;
    private Cidade cidade;

    public List<Estado> consultaEstados(String filtro) {
        try {
            estados = regiaoService.consultarEstados(filtro);
        } catch (BusinessException e) {
            estados = new ArrayList<>();
            Messages.addErrorMessage(e);
        }
        return estados;
    }

    public List<Cidade> consultarCidades(String filtro) {
        try {
            cidades = regiaoService.consultarCidades(estado.getIdEstado(), filtro);
        } catch (BusinessException e) {
            cidades = new ArrayList<>();
            Messages.addErrorMessage(e);
        }
        return cidades;
    }

    public void adicionaRegiao() {
        if (cidade != null && turnoCargo.contemCidade(cidade) || estado != null && turnoCargo.contemEstado(estado)) {
            Messages.addErrorMessage("Esta região já foi adicionada para este cargo.");
        } else {
            TurnoCargoRegiao regiao = new TurnoCargoRegiao(cidade, estado);
            turnoCargo.getTurnoCargoRegioes().add(regiao);
            cidade = null;
            estado = null;

            if (isDesabilitaQtdCadeiras())
                regiao.setQtdCadeiras(1);
        }
    }

    public void removerRegiao(TurnoCargoRegiao regiao) {
        turnoCargo.getTurnoCargoRegioes().remove(regiao);
    }

    public boolean isDesabilitaEstado() {
        return turnoCargo.getCargo().getNome().equals("Presidente");
    }

    public boolean isDesabilitaCidade() {
        return estado == null
                || !turnoCargo.getTipoCargo().equals(TipoCargo.MUNICIPAL.getTipo());
    }

    public boolean isDesabilitaAdicionar() {
        switch (turnoCargo.getTipoCargoEnum()) {
            case FEDERAL:
                return estado == null || turnoCargo.getCargo().getNome().equals("Presidente");
            case ESTADUAL:
                return estado == null;
            case MUNICIPAL:
                return estado == null || cidade == null;
            default:
                return true;
        }
    }

    public boolean isDesabilitaQtdCadeiras() {
        return turnoCargo.getCargo().getSistemaEleicao().equals(SistemaEleicao.MAIORIA_SIMPLES.getTipo());
    }

//    GETTERS E SETTERS

    public TurnoCargo getTurnoCargo() {
        return turnoCargo;
    }

    public void setTurnoCargo(TurnoCargo turnoCargo) {
        this.turnoCargo = turnoCargo;
        this.cidade = null;
        this.estado = null;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }
}
