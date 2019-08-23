package br.com.svo.util;

import org.omnifaces.util.Faces;

import javax.faces.context.FacesContext;
import java.io.IOException;

public class RedirectUtils {

    private static final String SVO_URL = System.getProperty("svo.url");

    public static void redirect(String url) {
        Faces.redirect(SVO_URL + "pages/" + url);
    }

    public static void redirectToLogin() {
        Faces.redirect(SVO_URL + "login.html");
    }
}
