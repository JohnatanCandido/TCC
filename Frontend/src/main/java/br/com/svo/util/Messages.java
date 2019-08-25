package br.com.svo.util;

import br.com.svo.business.exception.BusinessException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

public class Messages implements Serializable {

    public static final long serialVersionUID = 1L;

    public static void addMessage(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, message);
    }

    public static void addErrorMessage(String message) {
        addMessage(FacesMessage.SEVERITY_ERROR, message);
    }

    public static void addErrorMessage(BusinessException e) {
        addErrorMessage(e.getMessage());
        e.getMessages().forEach(m -> addErrorMessage(" - " + m));
    }

    private static void addMessage(FacesMessage.Severity severity, String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, null, message));
    }
}
