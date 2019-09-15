package br.com.svo.entities;

import java.io.Serializable;

public class Estado implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idEstado;
    private String nome;
    private String sigla;

    public Estado() {}

    public Estado(Long idEstado, String nome) {
        this.idEstado = idEstado;
        this.nome = nome;
    }

    //    GETTERS E SETTERS

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Estado))
            return false;
        Estado other = (Estado) obj;
        return this.idEstado.equals(other.idEstado);
    }
}
