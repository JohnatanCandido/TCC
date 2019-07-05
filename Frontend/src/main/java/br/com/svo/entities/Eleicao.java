package br.com.svo.entities;

import java.io.Serializable;

public class Eleicao implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idEleicao;

    private Integer ano;

//    GETTERS E SETTERS

    public Long getIdEleicao() {
        return idEleicao;
    }

    public void setIdEleicao(Long idEleicao) {
        this.idEleicao = idEleicao;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }
}
