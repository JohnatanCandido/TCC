package br.com.svo.business.login;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Identity;
import br.com.svo.entities.Login;
import br.com.svo.util.RestUtil;
import br.com.svo.util.exception.RestException;
import com.google.gson.Gson;

import java.security.NoSuchAlgorithmException;

public class LoginBusiness {

    private static final Gson GSON = new Gson();

    public Identity login(Login login) throws BusinessException {
        try {
            login.encriptaCredenciais();
            String response = new RestUtil("login/autenticar").withBody(login)
                                                              .withHeader("Content-Type", "application/json")
                                                              .post();
            return GSON.fromJson(response, Identity.class);
        } catch (RestException e) {
            throw new BusinessException(e.getMessages().get(0));
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException("Erro ao criptografar senha.");
        }
    }

//    public Identity login(Login login) throws BusinessException {
//        if (!login.getUsuario().equals("teste") || !login.getSenha().equals("teste"))
//            throw new BusinessException();
//        Identity identity = new Identity();
//        identity.setPessoa(new Pessoa());
//        identity.getPessoa().setNome("Teste");
//        return identity;
//    }
}
