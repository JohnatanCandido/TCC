package br.com.svo.entities;

import java.io.Serializable;

public class VotoApurado implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idVotoApurado;
    private TurnoCargo turnoCargo;
    private Cidade cidade;
    private Candidato candidato;
    private String idEleitor;

//    GETTERS E SETTERS

    public Long getIdVotoApurado() {
        return idVotoApurado;
    }

    public void setIdVotoApurado(Long idVotoApurado) {
        this.idVotoApurado = idVotoApurado;
    }

    public TurnoCargo getTurnoCargo() {
        return turnoCargo;
    }

    public void setTurnoCargo(TurnoCargo turnoCargo) {
        this.turnoCargo = turnoCargo;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public String getIdEleitor() {
        return idEleitor;
    }

    public void setIdEleitor(String idEleitor) {
        this.idEleitor = idEleitor;
    }
}
