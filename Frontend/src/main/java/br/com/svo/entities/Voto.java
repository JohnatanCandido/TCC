package br.com.svo.entities;

import java.io.Serializable;

public class Voto implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idEleitor;

    private Long idEleicao;

    private Long idCargo;

    private Long idCandidato;

//    GETTERS E SETTERS

    public Long getIdEleitor() {
        return idEleitor;
    }

    public void setIdEleitor(Long idEleitor) {
        this.idEleitor = idEleitor;
    }

    public Long getIdEleicao() {
        return idEleicao;
    }

    public void setIdEleicao(Long idEleicao) {
        this.idEleicao = idEleicao;
    }

    public Long getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Long idCargo) {
        this.idCargo = idCargo;
    }

    public Long getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Long idCandidato) {
        this.idCandidato = idCandidato;
    }
}
