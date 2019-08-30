package br.com.svo.util.exception;

import java.util.List;

public class RestException extends Exception {

    private List<String> messages;

    public RestException() {
        super();
    }

    public RestException(String message) {
        super(message);
    }

    public RestException(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
}
