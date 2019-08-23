package br.com.svo.entities;

import br.com.svo.entities.enums.SistemaEleicao;
import br.com.svo.entities.enums.TipoCargo;

import java.io.Serializable;

public class Cargo implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idCargo;
    private String nome;
    private SistemaEleicao sistemaEleicao;
    private boolean permiteSegundoTurno;
    private TipoCargo tipoCargo;

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

    public SistemaEleicao getSistemaEleicao() {
        return sistemaEleicao;
    }

    public void setSistemaEleicao(SistemaEleicao sistemaEleicao) {
        this.sistemaEleicao = sistemaEleicao;
    }

    public boolean isPermiteSegundoTurno() {
        return permiteSegundoTurno;
    }

    public void setPermiteSegundoTurno(boolean permiteSegundoTurno) {
        this.permiteSegundoTurno = permiteSegundoTurno;
    }

    public TipoCargo getTipoCargo() {
        return tipoCargo;
    }

    public void setTipoCargo(TipoCargo tipoCargo) {
        this.tipoCargo = tipoCargo;
    }
}
