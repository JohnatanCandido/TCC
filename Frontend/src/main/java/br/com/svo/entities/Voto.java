package br.com.svo.entities;

import br.com.svo.util.EncryptionUtils;

import java.io.Serializable;

public class Voto implements Serializable {

    public static final long serialVersionUID = 1L;

    private String idEleitor;

    private String idEleicao;

    private String idCargo;

    private String idCandidato;

//    GETTERS E SETTERS

    public String getIdEleitor() {
        return idEleitor;
    }

    public void setIdEleitor(String idEleitor) {
        this.idEleitor = idEleitor;
    }

    public String getIdEleicao() {
        return idEleicao;
    }

    public void setIdEleicao(String idEleicao) {
        this.idEleicao = idEleicao;
    }

    public String getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(String idCargo) {
        this.idCargo = idCargo;
    }

    public String getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(String idCandidato) {
        idCandidato = EncryptionUtils.encrypt(idCandidato).toString();
        this.idCandidato = idCandidato;
    }
}
