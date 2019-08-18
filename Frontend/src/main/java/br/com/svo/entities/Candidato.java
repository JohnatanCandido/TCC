package br.com.svo.entities;

import java.io.Serializable;

public class Candidato implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idCandidato;
    private Integer numero;
    private Partido partido;
    private EleicaoCargo eleicaoCargo;
    private Pessoa pessoa;

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

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public EleicaoCargo getEleicaoCargo() {
        return eleicaoCargo;
    }

    public void setEleicaoCargo(EleicaoCargo eleicaoCargo) {
        this.eleicaoCargo = eleicaoCargo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
