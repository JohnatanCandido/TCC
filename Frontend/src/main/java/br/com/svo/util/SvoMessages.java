package br.com.svo.util;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.business.exception.NoResultException;
import org.omnifaces.util.Messages;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

public class SvoMessages implements Serializable {

    public static final long serialVersionUID = 1L;

    public static void addMessage(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, message);
    }

    public static void addErrorMessage(String message) {
        addMessage(FacesMessage.SEVERITY_ERROR, message);
    }

    public static void addErrorMessage(NoResultException e) {
        addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
    }

    public static void addErrorMessage(BusinessException e) {
        addErrorMessage(e.getMessage());
        e.getMessages().forEach(m -> addErrorMessage(" - " + m));
    }

    public static void addFlashGlobalError(BusinessException e) {
        if (e.getMessage() != null)
            Messages.addFlashGlobalError(e.getMessage());
        for (String m: e.getMessages())
            Messages.addFlashGlobalError(m);
    }

    public static void addFlashGlobalError(String message) {
        if (message != null)
            Messages.addFlashGlobalError(message);
    }

    public static void addFlash(String message) {
        Messages.addFlashGlobalInfo(message);
    }

    private static void addMessage(FacesMessage.Severity severity, String message) {
        Messages.add(null, new FacesMessage(severity, null, message));
    }

    public static void addFoundMessage(int numeroRegistros) {
        String message;
        if (numeroRegistros == 1)
            message = "1 registro encontrado.";
        else
            message = String.format("%s registros encontrados", numeroRegistros);
        addMessage(message);
    }
}
