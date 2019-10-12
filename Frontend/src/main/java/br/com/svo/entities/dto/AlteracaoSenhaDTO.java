package br.com.svo.entities.dto;

import br.com.svo.util.EncryptionUtils;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

public class AlteracaoSenhaDTO implements Serializable {

    public static final long serialVersionUID = 1L;

    private String usuario;
    private String senha;
    private String senhaNova;
    private String confirmacaoSenhaNova;

    public void encriptarDados() {
        try {
            usuario = EncryptionUtils.encryptMD5(usuario);
            senha = EncryptionUtils.encryptMD5(senha);
            senhaNova = EncryptionUtils.encryptMD5(senhaNova);
            confirmacaoSenhaNova = EncryptionUtils.encryptMD5(confirmacaoSenhaNova);
        } catch (NoSuchAlgorithmException ignored) {}
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

    public String getSenhaNova() {
        return senhaNova;
    }

    public void setSenhaNova(String senhaNova) {
        this.senhaNova = senhaNova;
    }

    public String getConfirmacaoSenhaNova() {
        return confirmacaoSenhaNova;
    }

    public void setConfirmacaoSenhaNova(String confirmacaoSenhaNova) {
        this.confirmacaoSenhaNova = confirmacaoSenhaNova;
    }
}
