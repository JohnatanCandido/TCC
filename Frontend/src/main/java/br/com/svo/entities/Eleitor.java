package br.com.svo.entities;

import java.io.Serializable;

public class Eleitor implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idEleitor;
    private Cidade cidade;
    private String zonaEleitoral;
    private String numeroInscricao;
    private String secao;

//    GETTERS E SETTERS

    public Long getIdEleitor() {
        return idEleitor;
    }

    public void setIdEleitor(Long idEleitor) {
        this.idEleitor = idEleitor;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getZonaEleitoral() {
        return zonaEleitoral;
    }

    public void setZonaEleitoral(String zonaEleitoral) {
        this.zonaEleitoral = zonaEleitoral;
    }

    public String getNumeroInscricao() {
        return numeroInscricao;
    }

    public void setNumeroInscricao(String numeroInscricao) {
        this.numeroInscricao = numeroInscricao;
    }

    public String getSecao() {
        return secao;
    }

    public void setSecao(String secao) {
        this.secao = secao;
    }
}
