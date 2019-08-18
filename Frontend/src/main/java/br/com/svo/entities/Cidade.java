package br.com.svo.entities;

import java.io.Serializable;

public class Cidade implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idCidade;
    private Estado estado;
    private String nome;

//    GETTERS E SETTERS

    public Long getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(Long idCidade) {
        this.idCidade = idCidade;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
