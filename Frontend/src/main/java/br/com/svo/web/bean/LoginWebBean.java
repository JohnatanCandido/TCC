package br.com.svo.web.bean;

import br.com.svo.entities.Login;
import br.com.svo.util.EncryptionUtils;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named("loginWebBean")
public class LoginWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    private Login login;

    @PostConstruct
    public void init() {
        this.login = new Login();
    }

    public String getKey() {
        return EncryptionUtils.getKey();
    }

//    GETTERS E SETTERS

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
