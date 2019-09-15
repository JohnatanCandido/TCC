package br.com.svo.web.eleicao.web.bean;

import br.com.svo.entities.Cidade;
import br.com.svo.entities.Estado;
import br.com.svo.entities.TurnoCargo;
import br.com.svo.entities.TurnoCargoRegiao;
import br.com.svo.entities.enums.TipoCargo;
import br.com.svo.service.eleicao.EleicaoServiceLocal;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FiltroCandidatoWebBean {

    protected List<TurnoCargo> turnoCargos;
    protected List<Estado> estados = new ArrayList<>();
    protected List<Cidade> cidades = new ArrayList<>();

    protected TurnoCargo turnoCargo;
    protected Estado estado;
    protected Cidade cidade;

    @Inject
    protected EleicaoWebBean eleicaoWebBean;

    @Inject
    protected EleicaoServiceLocal eleicaoService;

    public void filtraEstados() {
        estado = null;
        cidade = null;

        if (turnoCargo != null && isRenderizaEstado()) {
            this.estados = turnoCargo.getTurnoCargoRegioes()
                                     .stream()
                                     .map(TurnoCargoRegiao::getEstado)
                                     .filter(Objects::nonNull)
                                     .collect(Collectors.toList());
            this.cidades = new ArrayList<>();
        }
    }

    public void filtraCidades() {
        cidade = null;
        if (isRenderizaCidade()) {
            cidades = turnoCargo.getTurnoCargoRegioes()
                                .stream()
                                .filter(tcr -> tcr.getEstado().equals(estado))
                                .map(TurnoCargoRegiao::getCidade)
                                .collect(Collectors.toList());
        }
    }

    public boolean isRenderizaEstado() {
        return turnoCargo != null && !turnoCargo.getCargo().getNome().equals("Presidente");
    }

    public boolean isRenderizaCidade() {
        return estado != null
                && turnoCargo.getTipoCargo().equals(TipoCargo.MUNICIPAL.getTipo());
    }

//    GETTERS E SETTERS

    public List<TurnoCargo> getTurnoCargos() {
        return turnoCargos;
    }

    public void setTurnoCargos(List<TurnoCargo> turnoCargos) {
        this.turnoCargos = turnoCargos;
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
