package br.com.svo.entities;

import java.io.Serializable;

public class Perfil implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idPerfil;
    private String nome;

//    GETTERS E SETTERS

    public Long getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
