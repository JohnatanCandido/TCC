package br.com.svo.entities.dto;

import java.io.Serializable;

public class ApuracaoCandidatoDTO implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idCandidato;
    private Integer numero;
    private String nome;
    private String estado;
    private String nomePartido;
    private String siglaPartido;
    private Integer numeroPartido;
    private Long votos;
    private String situacao;

    private String nomeVice;
    private String nomePartidoVice;
    private String siglaPartidoVice;
    private Integer numeroPartidoVice;

    public String getPartidoFormatado() {
        if (estado != null)
            return siglaPartido + " - " + estado;
        return siglaPartido;
    }

    public String getToolTipPartido() {
        return numeroPartido + " - " + nomePartido + " (" + siglaPartido + ")";
    }

    public String getPartidoViceFormatado() {
        if (estado != null)
            return siglaPartidoVice + " - " + estado;
        return siglaPartidoVice;
    }

    public String getToolTipPartidoVice() {
        return numeroPartidoVice + " - " + nomePartidoVice + " (" + siglaPartidoVice + ")";
    }

//    GETTERS E SETTERS

    public Long getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Long idCandidato) {
        this.idCandidato = idCandidato;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNomePartido() {
        return nomePartido;
    }

    public void setNomePartido(String nomePartido) {
        this.nomePartido = nomePartido;
    }

    public String getSiglaPartido() {
        return siglaPartido;
    }

    public void setSiglaPartido(String siglaPartido) {
        this.siglaPartido = siglaPartido;
    }

    public Integer getNumeroPartido() {
        return numeroPartido;
    }

    public void setNumeroPartido(Integer numeroPartido) {
        this.numeroPartido = numeroPartido;
    }

    public Long getVotos() {
        return votos;
    }

    public void setVotos(Long votos) {
        this.votos = votos;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getNomeVice() {
        return nomeVice;
    }

    public void setNomeVice(String nomeVice) {
        this.nomeVice = nomeVice;
    }

    public String getNomePartidoVice() {
        return nomePartidoVice;
    }

    public void setNomePartidoVice(String nomePartidoVice) {
        this.nomePartidoVice = nomePartidoVice;
    }

    public String getSiglaPartidoVice() {
        return siglaPartidoVice;
    }

    public void setSiglaPartidoVice(String siglaPartidoVice) {
        this.siglaPartidoVice = siglaPartidoVice;
    }

    public Integer getNumeroPartidoVice() {
        return numeroPartidoVice;
    }

    public void setNumeroPartidoVice(Integer numeroPartidoVice) {
        this.numeroPartidoVice = numeroPartidoVice;
    }
}
