package br.com.svo.web.eleicao.web.bean;

import br.com.svo.entities.*;
import br.com.svo.entities.enums.TipoCargo;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ViewScoped
@Named("candidatoCadastroWebBean")
public class CandidatoCadastroWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    private Candidato candidato;

    private List<TurnoCargo> turnoCargos;
    private List<Estado> estados = new ArrayList<>();
    private List<Cidade> cidades = new ArrayList<>();

    private TurnoCargo turnoCargo;
    private Estado estado;
    private Cidade cidade;

    @Inject
    private EleicaoWebBean eleicaoWebBean;

    @PostConstruct
    public void init() {
        this.turnoCargos = eleicaoWebBean.getEleicao().getTurnos().get(0).getTurnoCargos();
    }

    public void filtraEstados() {
        if (turnoCargo != null) {
            this.estados = eleicaoWebBean.getEleicao()
                                         .getTurnos()
                                         .get(0)
                                         .getTurnoCargos()
                                         .stream()
                                         .filter(tc -> tc.getCargo().equals(turnoCargo.getCargo()))
                                         .flatMap(tc -> tc.getTurnoCargoRegioes().stream().map(TurnoCargoRegiao::getEstado))
                                         .filter(Objects::nonNull)
                                         .collect(Collectors.toList());
        }
    }

    public void filtraCidades() {
        if (estado != null) {
            cidades = eleicaoWebBean.getEleicao()
                                    .getTurnos()
                                    .get(0)
                                    .getTurnoCargos()
                                    .stream()
                                    .filter(tc -> tc.equals(turnoCargo))
                                    .flatMap(tc -> tc.getTurnoCargoRegioes().stream().filter(tcr -> tcr.getEstado().equals(estado)).map(TurnoCargoRegiao::getCidade))
                                    .collect(Collectors.toList());
        }
    }

    public boolean isRenderizaEstado() {
        return turnoCargo != null;
    }

    public boolean isRenderizaCidade() {
        return estado != null
                && turnoCargo.getTipoCargo().equals(TipoCargo.MUNICIPAL.getTipo());
    }

//    GETTERS E SETTERS

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

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
