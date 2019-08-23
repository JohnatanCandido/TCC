package br.com.svo.entities;

import java.io.Serializable;

public class Estado implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idEstado;
    private String nome;

    public Estado() {}

    public Estado(Long idEstado, String nome) {
        this.idEstado = idEstado;
        this.nome = nome;
    }

    //    GETTERS E SETTERS

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
