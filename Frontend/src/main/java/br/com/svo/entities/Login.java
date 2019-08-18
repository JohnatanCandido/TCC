package br.com.svo.entities;

import java.io.Serializable;

public class Login implements Serializable {

    public static final long serialVersionUID = 1L;

    private String usuario;
    private String senha;

//    GETTERS E SETTERS

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
