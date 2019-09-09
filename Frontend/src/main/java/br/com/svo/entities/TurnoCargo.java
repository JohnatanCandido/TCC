package br.com.svo.entities;

import br.com.svo.entities.enums.TipoCargo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TurnoCargo implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idTurnoCargo;
    private Cargo cargo;
    private List<TurnoCargoRegiao> turnoCargoRegioes = new ArrayList<>();

    public TurnoCargo() {}

    public TurnoCargo(Cargo cargo) {
        this.cargo = cargo;
        if (cargo.getNome().equals("Presidente")) {
            TurnoCargoRegiao tcr = new TurnoCargoRegiao(1, true);
            turnoCargoRegioes.add(tcr);
        } else if (cargo.getTipoCargo().equals(TipoCargo.FEDERAL.getTipo())) {
            TurnoCargoRegiao tcr = new TurnoCargoRegiao(0, false);
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof TurnoCargo))
            return false;
        TurnoCargo other = (TurnoCargo) obj;
        return this.idTurnoCargo.equals(other.idTurnoCargo);
    }

    @Override
    public String toString() {
        return "TurnoCargo[" + idTurnoCargo + ", " + cargo.getNome() + "]";
    }
}
