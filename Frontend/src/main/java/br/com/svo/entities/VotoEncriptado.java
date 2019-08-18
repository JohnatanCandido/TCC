package br.com.svo.entities;

import java.io.Serializable;

public class VotoEncriptado implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idVotoEncriptado;
    private EleicaoCargo eleicaoCargo;
    private Cidade cidade;
    private String idCandidato;
    private String idEleitor;

//    GETTERS E SETTERS

    public Long getIdVotoEncriptado() {
        return idVotoEncriptado;
    }

    public void setIdVotoEncriptado(Long idVotoEncriptado) {
        this.idVotoEncriptado = idVotoEncriptado;
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

    public String getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(String idCandidato) {
        this.idCandidato = idCandidato;
    }

    public String getIdEleitor() {
        return idEleitor;
    }

    public void setIdEleitor(String idEleitor) {
        this.idEleitor = idEleitor;
    }
}
