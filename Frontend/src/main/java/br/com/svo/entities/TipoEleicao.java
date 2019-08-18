package br.com.svo.entities;

import java.io.Serializable;

public class TipoEleicao implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idTipoEleicao;
    private String nome;

//    GETTERS E SETTERS

    public Long getIdTipoEleicao() {
        return idTipoEleicao;
    }

    public void setIdTipoEleicao(Long idTipoEleicao) {
        this.idTipoEleicao = idTipoEleicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
