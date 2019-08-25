package br.com.svo.business.login;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Identity;
import br.com.svo.entities.Login;
import br.com.svo.entities.Pessoa;
import br.com.svo.util.RestUtil;
import com.google.gson.Gson;

import javax.inject.Inject;

public class LoginBusiness {

    private static final Gson GSON = new Gson();

    @Inject
    private RestUtil restUtil;

//    public Identity login(Login login) throws BusinessException {
//        try {
//            login.encriptaSenha();
//            String response = restUtil.httpPost("login/autenticar", login);
//            return GSON.fromJson(response, Identity.class);
//        } catch (RestException e) {
//            throw new BusinessException(e.getMessages().get(0));
//        } catch (NoSuchAlgorithmException e) {
//            throw new BusinessException("Erro ao criptografar senha.");
//        }
//    }

    public Identity login(Login login) throws BusinessException {
        if (!login.getUsuario().equals("teste") || !login.getSenha().equals("teste"))
            throw new BusinessException();
        Identity identity = new Identity();
        identity.setPessoa(new Pessoa());
        identity.getPessoa().setNome("Teste");
        return identity;
    }
}
