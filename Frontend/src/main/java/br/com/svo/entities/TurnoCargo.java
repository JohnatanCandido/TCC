package br.com.svo.entities;

import br.com.svo.entities.enums.TipoCargo;

import java.io.Serializable;
import java.util.List;

public class TurnoCargo implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idTurnoCargo;
    private Turno turno;
    private Cargo cargo;
    private List<TurnoCargoRegiao> turnoCargoRegioes;

    public TurnoCargo() {}

    public TurnoCargo(Turno turno, Cargo cargo) {
        this.turno = turno;
        this.cargo = cargo;
    }

    public TipoCargo getTipoCargo() {
        return cargo.getTipoCargo();
    }

    //    GETTERS E SETTERS

    public Long getIdTurnoCargo() {
        return idTurnoCargo;
    }

    public void setIdTurnoCargo(Long idTurnoCargo) {
        this.idTurnoCargo = idTurnoCargo;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
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
}
