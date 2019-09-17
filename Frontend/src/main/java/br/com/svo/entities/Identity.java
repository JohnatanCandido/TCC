package br.com.svo.entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@Named("identity")
public class Identity implements Serializable {

    public static final long serialVersionUID = 1L;

    private Pessoa pessoa;
    private List<Perfil> perfis = new ArrayList<>();
    private String token;

    public void init(Identity identity) {
        this.pessoa = identity.getPessoa();
        this.perfis = identity.getPerfis();
        this.token = identity.getToken();
    }

    public void logour() {
        this.pessoa = null;
        this.perfis = null;
        this.token = null;
    }

    public boolean hasPerfil(String perfil) {
        return perfis.stream().anyMatch(p -> p.getNome().equals(perfil));
    }

//    GETTERS E SETTERS

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Perfil> getPerfis() {
        return perfis;
    }

    public String getToken() {
        return token;
    }
}
