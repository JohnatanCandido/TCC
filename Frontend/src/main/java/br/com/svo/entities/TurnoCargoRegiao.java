package br.com.svo.entities;

import br.com.svo.entities.enums.TipoCargo;

import java.io.Serializable;

public class TurnoCargoRegiao implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idTurnoCargoRegiao;
    private TurnoCargo turnoCargo;
    private int qtdCadeiras;
    private boolean possuiSegundoTurno;
    private Cidade cidade;
    private Estado estado;

    public TurnoCargoRegiao() {}

    public TurnoCargoRegiao(TurnoCargo turnoCargo, Cidade cidade, Estado estado) {
        this.turnoCargo = turnoCargo;
        this.cidade = cidade;
        this.estado = estado;
    }

    //    GETTERS E SETTERS

    public Long getIdTurnoCargoRegiao() {
        return idTurnoCargoRegiao;
    }

    public void setIdTurnoCargoRegiao(Long idTurnoCargoRegiao) {
        this.idTurnoCargoRegiao = idTurnoCargoRegiao;
    }

    public TurnoCargo getTurnoCargo() {
        return turnoCargo;
    }

    public void setTurnoCargo(TurnoCargo turnoCargo) {
        this.turnoCargo = turnoCargo;
    }

    public int getQtdCadeiras() {
        return qtdCadeiras;
    }

    public void setQtdCadeiras(int qtdCadeiras) {
        this.qtdCadeiras = qtdCadeiras;
    }

    public boolean isPossuiSegundoTurno() {
        return possuiSegundoTurno;
    }

    public void setPossuiSegundoTurno(boolean possuiSegundoTurno) {
        this.possuiSegundoTurno = possuiSegundoTurno;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
