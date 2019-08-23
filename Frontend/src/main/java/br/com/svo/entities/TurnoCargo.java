package br.com.svo.entities;

import java.io.Serializable;

public class EleicaoCargo implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idEleicaoCargo;
    private Eleicao eleicao;
    private Cargo cargo;
    private Integer qtdCadeiras;

//    GETTERS E SETTERS

    public Long getIdEleicaoCargo() {
        return idEleicaoCargo;
    }

    public void setIdEleicaoCargo(Long idEleicaoCargo) {
        this.idEleicaoCargo = idEleicaoCargo;
    }

    public Eleicao getEleicao() {
        return eleicao;
    }

    public void setEleicao(Eleicao eleicao) {
        this.eleicao = eleicao;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Integer getQtdCadeiras() {
        return qtdCadeiras;
    }

    public void setQtdCadeiras(Integer qtdCadeiras) {
        this.qtdCadeiras = qtdCadeiras;
    }
}
