package br.com.svo.web.eleicao.web.bean;

import br.com.svo.entities.Cidade;
import br.com.svo.entities.Estado;
import br.com.svo.entities.TurnoCargo;
import br.com.svo.entities.TurnoCargoRegiao;
import br.com.svo.entities.enums.TipoCargo;
import br.com.svo.service.regiao.RegiaoServiceLocal;
import org.omnifaces.cdi.ViewScoped;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named("eleicaoModalCargoWebBean")
public class EleicaoModalCargoWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    @Inject
    private RegiaoServiceLocal regiaoService;

    private TurnoCargo turnoCargo;

    private Estado estado;
    private Cidade cidade;

    public List<Estado> consultaEstados(String filtro) {
        return regiaoService.consultarEstados(filtro);
    }

    public List<Cidade> consultarCidades(String filtro) {
        return regiaoService.consultarCidades(estado.getIdEstado(), filtro);
    }

    public void adicionaRegiao() {
        TurnoCargoRegiao regiao = new TurnoCargoRegiao(turnoCargo, cidade, estado);
        turnoCargo.getTurnoCargoRegioes().add(regiao);
        cidade = null;
        estado = null;
    }

    public boolean isDesabilitaEstado() {
        return turnoCargo.getTipoCargo().equals(TipoCargo.FEDERAL);
    }

    public boolean isDesabilitaCidade() {
        return estado == null
                || !turnoCargo.getTipoCargo().equals(TipoCargo.MUNICIPAL);
    }

    public boolean isDesabilitaAdicionar() {
        switch (turnoCargo.getTipoCargo()) {
            case ESTADUAL:
                return estado == null;
            case MUNICIPAL:
                return estado == null || cidade == null;
            default:
                return true;
        }
    }

//    GETTERS E SETTERS

    public TurnoCargo getTurnoCargo() {
        return turnoCargo;
    }

    public void setTurnoCargo(TurnoCargo turnoCargo) {
        this.turnoCargo = turnoCargo;
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
}
