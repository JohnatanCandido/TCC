package br.com.svo.entities.dto;

import java.io.Serializable;

public class CandidatoDTO implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private boolean isCandidato;
    private String partido;
    private String vice;
    private String partidoVice;

//    GETTERS E SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isCandidato() {
        return isCandidato;
    }

    public void setCandidato(boolean candidato) {
        isCandidato = candidato;
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
