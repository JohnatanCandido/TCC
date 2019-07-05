package br.com.svo.entities;

import java.io.Serializable;
import java.util.Date;

public class Apuracao implements Serializable {

    public static final long serialVersionUID = 1L;

    private Eleicao eleicao;

    private Date dataApuracao;

//    GETTERS E SETTERS

    public Eleicao getEleicao() {
        return eleicao;
    }

    public void setEleicao(Eleicao eleicao) {
        this.eleicao = eleicao;
    }

    public Date getDataApuracao() {
        return dataApuracao;
    }

    public void setDataApuracao(Date dataApuracao) {
        this.dataApuracao = dataApuracao;
    }
}
