package br.com.svo.entities;

import java.io.Serializable;

public class Eleitor implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idEleitor;
    private Pessoa pessoa;
    private Cidade cidade;
    private Integer zonaEleitoral;
    private Integer numeroInscricao;

//    GETTERS E SETTERS

    public Long getIdEleitor() {
        return idEleitor;
    }

    public void setIdEleitor(Long idEleitor) {
        this.idEleitor = idEleitor;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Integer getZonaEleitoral() {
        return zonaEleitoral;
    }

    public void setZonaEleitoral(Integer zonaEleitoral) {
        this.zonaEleitoral = zonaEleitoral;
    }

    public Integer getNumeroInscricao() {
        return numeroInscricao;
    }

    public void setNumeroInscricao(Integer numeroInscricao) {
        this.numeroInscricao = numeroInscricao;
    }
}
