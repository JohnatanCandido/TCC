package br.com.svo.util;

import javax.faces.context.FacesContext;
import java.io.IOException;

public class RedirectUtils {

    private static final String SVO_URL = System.getProperty("svo.url");

    public static void redirect(String url) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(SVO_URL + "pages/" + url);
    }

    public static void redirectToLogin() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(SVO_URL + "login.xhtml");
    }
}
