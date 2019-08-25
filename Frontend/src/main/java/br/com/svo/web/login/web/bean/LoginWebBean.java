package br.com.svo.web.login.web.bean;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Identity;
import br.com.svo.entities.Login;
import br.com.svo.service.login.LoginServiceLocal;
import br.com.svo.util.EncryptionUtils;
import br.com.svo.util.IdentityUtil;
import br.com.svo.util.Messages;
import br.com.svo.util.RedirectUtils;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named("loginWebBean")
public class LoginWebBean implements Serializable {

    /*
        Iniciar postgres:

        cd Program Files\PostgreSQL\11\bin
        pg_ctl -D "C:\Program Files\PostgreSQL\11\data" start
     */

    public static final long serialVersionUID = 1L;

    private Login login;

    @Inject
    private Identity identity;

    @Inject
    private LoginServiceLocal loginService;

    @PostConstruct
    public void init() {
        this.login = new Login();
    }

    public String getKey() {
        return EncryptionUtils.getKey();
    }

    public void login() {
        try {
            identity.init(loginService.login(login));
            IdentityUtil.login(identity);
            RedirectUtils.redirect("index.html");
        } catch (BusinessException e) {
            Messages.addErrorMessage(e);
        }
    }

    public void logout() {
        IdentityUtil.logout();
        RedirectUtils.redirectToLogin();
    }

//    GETTERS E SETTERS

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
