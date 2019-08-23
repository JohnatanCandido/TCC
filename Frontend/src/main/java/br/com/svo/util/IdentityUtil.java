package br.com.svo.util;

import br.com.svo.entities.Identity;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class IdentityUtil {

    private IdentityUtil() {}

    public static Identity getUser() {
        return (Identity) currentExternalContext().getSessionMap().get("user");
    }

    public static void login(Identity identity) {
        currentExternalContext().getSessionMap().put("user", identity);
    }

    public static void logout() {
        currentExternalContext().getSessionMap().clear();
        currentExternalContext().invalidateSession();
    }

    private static ExternalContext currentExternalContext() {
        if (FacesContext.getCurrentInstance() == null) {
            throw new RuntimeException("O FacesContext não pode ser chamado fora de uma requisição HTTP");
        } else {
            return FacesContext.getCurrentInstance().getExternalContext();
        }
    }
}
