package br.com.svo.entities;

import java.io.Serializable;

public class Eleitor implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idEleitor;

    private String nome;

    private Integer zona;

    private Integer numeroInscricao;

//    GETTERS E SETTERS

    public Long getIdEleitor() {
        return idEleitor;
    }

    public void setIdEleitor(Long idEleitor) {
        this.idEleitor = idEleitor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getZona() {
        return zona;
    }

    public void setZona(Integer zona) {
        this.zona = zona;
    }

    public Integer getNumeroInscricao() {
        return numeroInscricao;
    }

    public void setNumeroInscricao(Integer numeroInscricao) {
        this.numeroInscricao = numeroInscricao;
    }
}
