package br.com.svo.web.login.web.bean;

import br.com.svo.entities.Identity;
import br.com.svo.entities.Voto;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named("testeWebBean")
public class TesteWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    private Voto voto;

    @Inject
    private Identity identity;

    @PostConstruct
    public void init() {
        voto = new Voto();
    }

    public void votar() {
        voto = new Voto();
    }

    public Voto getVoto() {
        return voto;
    }

    public void setVoto(Voto voto) {
        this.voto = voto;
    }
}
