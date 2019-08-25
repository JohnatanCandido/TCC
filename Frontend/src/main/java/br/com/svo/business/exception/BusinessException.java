package br.com.svo.business.exception;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception {

    private List<String> messages = new ArrayList<>();

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, List<String> messages) {
        super(message);
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
}
