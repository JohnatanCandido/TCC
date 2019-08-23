package br.com.svo.business;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Identity;
import br.com.svo.entities.Login;
import br.com.svo.entities.Pessoa;
import br.com.svo.util.RestUtil;
import br.com.svo.util.exception.RestException;
import com.google.gson.Gson;

public class LoginBusiness {

    private static final Gson GSON = new Gson();

    public Identity login(Login login) throws BusinessException {
//        try {
//            String response = RestUtil.httpPost("login/autenticar", login);
//            return GSON.fromJson(response, Identity.class);
            Identity identity = new Identity();
            identity.setPessoa(new Pessoa());
            identity.getPessoa().setNome("Teste");
            return identity;
//        } catch (RestException e) {
//            throw new BusinessException("Login ou senha incorretos");
//        }
    }
}
