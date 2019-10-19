package br.com.svo.web.login.web.bean;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Identity;
import br.com.svo.entities.Login;
import br.com.svo.service.login.LoginServiceLocal;
import br.com.svo.util.EncryptionUtils;
import br.com.svo.util.IdentityUtil;
import br.com.svo.util.Perfis;
import br.com.svo.util.RedirectUtils;
import br.com.svo.util.exception.RestException;
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

    private boolean loginFalhou;
    private String usuario;

    @PostConstruct
    public void init() {
        this.login = new Login();
    }

    public void login() {
        try {
            usuario = login.getUsuario();
            identity.init(loginService.login(login));
            IdentityUtil.login(identity);
            RedirectUtils.redirectToHome();
        } catch (BusinessException e) {
            loginFalhou = true;
            login.setUsuario(usuario);
        }
    }

    public void logout() {
        IdentityUtil.logout();
        RedirectUtils.redirectToLogin();
    }

    public boolean isRenderizaMenuPessoa() {
        return identity != null && identity.hasPerfil(Perfis.ADMINISTRADOR);
    }

//    GETTERS E SETTERS

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public boolean isLoginFalhou() {
        return loginFalhou;
    }
}
