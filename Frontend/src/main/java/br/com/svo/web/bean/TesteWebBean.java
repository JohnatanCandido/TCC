package br.com.svo.web.bean;

import br.com.svo.entities.Identity;
import br.com.svo.entities.VotoEncriptado;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named("testeWebBean")
public class TesteWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    private VotoEncriptado votoEncriptado;

    private Identity identity = (Identity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

    @PostConstruct
    public void init() {
        votoEncriptado = new VotoEncriptado();
    }

    public void votar() {
        votoEncriptado = new VotoEncriptado();
    }

    public VotoEncriptado getVotoEncriptado() {
        return votoEncriptado;
    }

    public void setVotoEncriptado(VotoEncriptado votoEncriptado) {
        this.votoEncriptado = votoEncriptado;
    }
}
