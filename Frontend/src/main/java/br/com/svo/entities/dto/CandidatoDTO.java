package br.com.svo.entities.dto;

import java.io.Serializable;

public class CandidatoDTO implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idCandidato;
    private Long idPartido;
    private String nome;
    private String partido;
    private String vice;
    private String partidoVice;

//    GETTERS E SETTERS

    public Long getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Long idCandidato) {
        this.idCandidato = idCandidato;
    }

    public Long getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(Long idPartido) {
        this.idPartido = idPartido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public String getVice() {
        return vice;
    }

    public void setVice(String vice) {
        this.vice = vice;
    }

    public String getPartidoVice() {
        return partidoVice;
    }

    public void setPartidoVice(String partidoVice) {
        this.partidoVice = partidoVice;
    }
}
