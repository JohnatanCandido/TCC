package br.com.svo.service.login;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Identity;
import br.com.svo.entities.Login;

import javax.ejb.Local;

@Local
public interface LoginServiceLocal {

    Identity login(Login login) throws BusinessException;
}
