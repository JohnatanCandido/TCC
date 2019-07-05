package br.com.svo.entities;

import java.io.Serializable;

public class Eleitor implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idEleitor;

    private String nome;

    private Integer zonaEleitoral;

    private Integer secao;

    private Integer tituloEleitoral;

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

    public Integer getZonaEleitoral() {
        return zonaEleitoral;
    }

    public void setZonaEleitoral(Integer zonaEleitoral) {
        this.zonaEleitoral = zonaEleitoral;
    }

    public Integer getSecao() {
        return secao;
    }

    public void setSecao(Integer secao) {
        this.secao = secao;
    }

    public Integer getTituloEleitoral() {
        return tituloEleitoral;
    }

    public void setTituloEleitoral(Integer tituloEleitoral) {
        this.tituloEleitoral = tituloEleitoral;
    }
}
