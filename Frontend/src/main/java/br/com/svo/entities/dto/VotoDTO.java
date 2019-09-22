package br.com.svo.entities.dto;

import java.io.Serializable;

public class VotoDTO implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idTurnoCargoRegiao;
    private String idCandidato;
    private String idPartido;

    public Long getIdTurnoCargoRegiao() {
        return idTurnoCargoRegiao;
    }

    public void setIdTurnoCargoRegiao(Long idTurnoCargoRegiao) {
        this.idTurnoCargoRegiao = idTurnoCargoRegiao;
    }

    public String getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(String idCandidato) {
        this.idCandidato = idCandidato;
    }

    public String getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(String idPartido) {
        this.idPartido = idPartido;
    }
}
