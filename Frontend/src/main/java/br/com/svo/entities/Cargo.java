package br.com.svo.entities;

import java.io.Serializable;

public class Cargo implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idCargo;
    private String nome;
    private String sistemaEleicao;
    private boolean permiteSegundoTurno;
    private String tipoCargo;
    private int tamNumeroCandiato;

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

    public String getSistemaEleicao() {
        return sistemaEleicao;
    }

    public void setSistemaEleicao(String sistemaEleicao) {
        this.sistemaEleicao = sistemaEleicao;
    }

    public boolean isPermiteSegundoTurno() {
        return permiteSegundoTurno;
    }

    public void setPermiteSegundoTurno(boolean permiteSegundoTurno) {
        this.permiteSegundoTurno = permiteSegundoTurno;
    }

    public String getTipoCargo() {
        return tipoCargo;
    }

    public void setTipoCargo(String tipoCargo) {
        this.tipoCargo = tipoCargo;
    }

    public int getTamNumeroCandiato() {
        return tamNumeroCandiato;
    }

    public void setTamNumeroCandiato(int tamNumeroCandiato) {
        this.tamNumeroCandiato = tamNumeroCandiato;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Cargo))
            return false;
        Cargo other = (Cargo) obj;
        return this.idCargo.equals(other.idCargo);
    }
}
