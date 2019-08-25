package br.com.svo.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login implements Serializable {

    public static final long serialVersionUID = 1L;

    private String usuario;
    private String senha;

    public void encriptaSenha() throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(senha.getBytes());
        senha = new BigInteger(1, m.digest()).toString(16).trim();
    }

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
