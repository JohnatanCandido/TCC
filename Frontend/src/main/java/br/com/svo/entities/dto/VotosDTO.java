package br.com.svo.entities.dto;

import java.io.Serializable;
import java.util.List;

public class VotosDTO implements Serializable {

    public static final long serialVersionUID = 1L;

    private String usuario;
    private String senha;
    private String pin;
    private List<VotoDTO> votos;

    public VotosDTO(String usuario, String senha, String pin, List<VotoDTO> votos) {
        this.usuario = usuario;
        this.senha = senha;
        this.pin = pin;
        this.votos = votos;
    }

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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public List<VotoDTO> getVotos() {
        return votos;
    }

    public void setVotos(List<VotoDTO> votos) {
        this.votos = votos;
    }
}
