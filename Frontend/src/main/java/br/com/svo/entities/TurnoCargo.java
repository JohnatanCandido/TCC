package br.com.svo.entities;

import br.com.svo.entities.enums.TipoCargo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TurnoCargo implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idTurnoCargo;
    private Long idTurno;
    private Cargo cargo;
    private List<TurnoCargoRegiao> turnoCargoRegioes = new ArrayList<>();

    public TurnoCargo() {}

    public TurnoCargo(Long idTurno, Cargo cargo) {
        this.idTurno = idTurno;
        this.cargo = cargo;
        if (cargo.getNome().equals("Presidente")) {
            TurnoCargoRegiao tcr = new TurnoCargoRegiao(1, true);
            turnoCargoRegioes.add(tcr);
        }
    }

    public String getTipoCargo() {
        return cargo.getTipoCargo();
    }

    public TipoCargo getTipoCargoEnum() {
        return TipoCargo.parse(cargo.getTipoCargo());
    }

    public boolean contemEstado(Estado estado) {
        return this.turnoCargoRegioes.stream().anyMatch(tcr -> tcr.getCidade() == null && tcr.getEstado().equals(estado));
    }

    public boolean contemCidade(Cidade cidade) {
        return this.turnoCargoRegioes.stream().anyMatch(tcr -> tcr.getCidade().equals(cidade));
    }

    public TurnoCargoRegiao turnoCargoRegiaoByEstado(Estado estado) {
        return filterByEstado(estado).filter(e -> e.getCidade() == null).findFirst().orElse(null);
    }

    public TurnoCargoRegiao turnoCargoRegiaoByCidade(Estado estado, Cidade cidade) {
        return filterByEstado(estado).filter(t -> t.getCidade().equals(cidade)).findFirst().orElse(null);
    }

    private Stream<TurnoCargoRegiao> filterByEstado(Estado estado) {
        return turnoCargoRegioes.stream().filter(t -> t.getEstado().equals(estado));
    }

//    GETTERS E SETTERS

    public Long getIdTurnoCargo() {
        return idTurnoCargo;
    }

    public void setIdTurnoCargo(Long idTurnoCargo) {
        this.idTurnoCargo = idTurnoCargo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public List<TurnoCargoRegiao> getTurnoCargoRegioes() {
        return turnoCargoRegioes;
    }

    public void setTurnoCargoRegioes(List<TurnoCargoRegiao> turnoCargoRegioes) {
        this.turnoCargoRegioes = turnoCargoRegioes;
    }

    public Long getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Long idTurno) {
        this.idTurno = idTurno;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof TurnoCargo))
            return false;
        TurnoCargo other = (TurnoCargo) obj;

        if (this.idTurnoCargo != null && other.idTurnoCargo != null)
            return this.idTurnoCargo.equals(other.idTurnoCargo);
        return this.cargo.equals(other.cargo);
    }

    @Override
    public String toString() {
        return "TurnoCargo[" + idTurnoCargo + ", " + cargo.getNome() + "]";
    }
}
