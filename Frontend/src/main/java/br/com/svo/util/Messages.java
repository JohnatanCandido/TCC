package br.com.svo.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;

public class Messages implements Serializable {

    public static final long serialVersionUID = 1L;

    public static void addMessage(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, message);
    }

    public static void addErrorMessage(String message) {
        addMessage(FacesMessage.SEVERITY_ERROR, message);
    }

    private static void addMessage(FacesMessage.Severity severity, String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, null, message));
    }
}
