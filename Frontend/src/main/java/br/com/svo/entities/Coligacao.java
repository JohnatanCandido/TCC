package br.com.svo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Coligacao implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idColigacao;
    private Long idEleicao;
    private String nome;
    private List<Partido> partidos = new ArrayList<>();

    public Coligacao() {}

    public Coligacao(Long idEleicao) {
        this.idEleicao = idEleicao;
    }

    //    GETTERS E SETTERS

    public Long getIdColigacao() {
        return idColigacao;
    }

    public void setIdColigacao(Long idColigacao) {
        this.idColigacao = idColigacao;
    }

    public Long getIdEleicao() {
        return idEleicao;
    }

    public void setIdEleicao(Long idEleicao) {
        this.idEleicao = idEleicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }
}
