package br.com.svo.entities;

import br.com.svo.util.EncryptionUtils;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login implements Serializable {

    public static final long serialVersionUID = 1L;

    private String usuario;
    private String senha;

    public void encriptaCredenciais() throws NoSuchAlgorithmException {
        usuario = EncryptionUtils.encryptMD5(usuario);
        senha = EncryptionUtils.encryptMD5(senha);
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
