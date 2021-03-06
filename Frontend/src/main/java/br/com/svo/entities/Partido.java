package br.com.svo.entities;

import java.io.Serializable;

public class Partido implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idPartido;
    private Integer numeroPartido;
    private String sigla;
    private String nome;
    private Long idColigacao;

    public String getNomeFormatado() {
        return nome + " (" + sigla + ")";
    }

    public String getInfoPartido() {
        return numeroPartido + " - " + getNomeFormatado();
    }

//    GETTERS E SETTERS

    public Long getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(Long idPartido) {
        this.idPartido = idPartido;
    }

    public Integer getNumeroPartido() {
        return numeroPartido;
    }

    public void setNumeroPartido(Integer numeroPartido) {
        this.numeroPartido = numeroPartido;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getIdColigacao() {
        return idColigacao;
    }

    public void setIdColigacao(Long idColigacao) {
        this.idColigacao = idColigacao;
    }
}
