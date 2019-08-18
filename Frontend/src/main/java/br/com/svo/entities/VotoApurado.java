package br.com.svo.entities;

import java.io.Serializable;

public class VotoApurado implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idVotoApurado;
    private EleicaoCargo eleicaoCargo;
    private Cidade cidade;
    private Candidato candidato;
    private String idEleitor;

//    GETTERS E SETTERS

    public Long getIdVotoApurado() {
        return idVotoApurado;
    }

    public void setIdVotoApurado(Long idVotoApurado) {
        this.idVotoApurado = idVotoApurado;
    }

    public EleicaoCargo getEleicaoCargo() {
        return eleicaoCargo;
    }

    public void setEleicaoCargo(EleicaoCargo eleicaoCargo) {
        this.eleicaoCargo = eleicaoCargo;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public String getIdEleitor() {
        return idEleitor;
    }

    public void setIdEleitor(String idEleitor) {
        this.idEleitor = idEleitor;
    }
}
