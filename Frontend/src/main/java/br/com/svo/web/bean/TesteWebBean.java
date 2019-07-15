package br.com.svo.web.bean;

import br.com.svo.entities.Voto;
import br.com.svo.util.RestUtil;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named("testeWebBean")
public class TesteWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    private Voto voto;

    @PostConstruct
    public void init() {
        voto = new Voto();
    }

    public void votar() {
        RestUtil.httpPost("votar", voto);
        voto = new Voto();
    }

    public Voto getVoto() {
        return voto;
    }

    public void setVoto(Voto voto) {
        this.voto = voto;
    }
}
