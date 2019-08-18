package br.com.svo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Identity implements Serializable {

    public static final long serialVersionUID = 1L;

    private Pessoa pessoa;
    private List<Perfil> perfis = new ArrayList<>();
    private String token;

//    GETTERS E SETTERS

    public Pessoa getPessoa() {
        return pessoa;
    }

    public List<Perfil> getPerfis() {
        return perfis;
    }

    public String getToken() {
        return token;
    }
}
