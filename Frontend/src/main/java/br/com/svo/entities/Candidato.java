package br.com.svo.entities;

import java.io.Serializable;

public class Candidato implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idCandidato;
    private Integer numero;
    private Partido partido;
    private TurnoCargoRegiao turnoCargoRegiao;
    private Pessoa pessoa;
    private Long idEleicao;
    private Candidato candidatoPrincipal;

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

    public TurnoCargoRegiao getTurnoCargoRegiao() {
        return turnoCargoRegiao;
    }

    public void setTurnoCargoRegiao(TurnoCargoRegiao turnoCargoRegiao) {
        this.turnoCargoRegiao = turnoCargoRegiao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Long getIdEleicao() {
        return idEleicao;
    }

    public void setIdEleicao(Long idEleicao) {
        this.idEleicao = idEleicao;
    }

    public Candidato getCandidatoPrincipal() {
        return candidatoPrincipal;
    }

    public void setCandidatoPrincipal(Candidato candidatoPrincipal) {
        this.candidatoPrincipal = candidatoPrincipal;
    }
}
