package br.com.svo.entities;

import br.com.svo.entities.enums.SistemaEleicao;

import java.io.Serializable;

public class Cargo implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idCargo;

    private String nome;

    private Integer numeroRepresentantes;

    private SistemaEleicao sistemaEleicao;

//    GETTERS E SETTERS

    public Long getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Long idCargo) {
        this.idCargo = idCargo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumeroRepresentantes() {
        return numeroRepresentantes;
    }

    public void setNumeroRepresentantes(Integer numeroRepresentantes) {
        this.numeroRepresentantes = numeroRepresentantes;
    }

    public SistemaEleicao getSistemaEleicao() {
        return sistemaEleicao;
    }

    public void setSistemaEleicao(SistemaEleicao sistemaEleicao) {
        this.sistemaEleicao = sistemaEleicao;
    }
}
