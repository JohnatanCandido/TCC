package br.com.svo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Turno implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idTurno;
    private int turno;
    private Date inicio;
    private Date termino;
    private List<TurnoCargo> turnoCargos = new ArrayList<>();

    public Turno() {}

    public Turno(Eleicao eleicao, int turno) {
        this.turno = turno;
    }

    public boolean contemCargo(Cargo cargo) {
        return this.turnoCargos.stream().anyMatch(tc -> tc.getCargo().equals(cargo));
    }

//    GETTERS E SETTERS

    public Long getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Long idTurno) {
        this.idTurno = idTurno;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getTermino() {
        return termino;
    }

    public void setTermino(Date termino) {
        this.termino = termino;
    }

    public List<TurnoCargo> getTurnoCargos() {
        return turnoCargos;
    }

    public void setTurnoCargos(List<TurnoCargo> turnoCargos) {
        this.turnoCargos = turnoCargos;
    }
}