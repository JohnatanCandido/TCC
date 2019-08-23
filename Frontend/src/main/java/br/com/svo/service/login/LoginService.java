package br.com.svo.service;

import br.com.svo.business.LoginBusiness;
import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Identity;
import br.com.svo.entities.Login;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;

@Stateless
public class LoginService implements LoginServiceLocal, Serializable {

    public static final long serialVersionUID = 1L;

    @Inject
    private LoginBusiness loginBusiness;

    @Override
    public Identity login(Login login) throws BusinessException {
        return loginBusiness.login(login);
    }
}
