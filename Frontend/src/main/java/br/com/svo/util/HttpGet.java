package br.com.svo.util;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

public class HttpGet extends HttpEntityEnclosingRequestBase {

    private final static String METHOD_NAME = "GET";

    public HttpGet(String uri) {
        this.setURI(URI.create(uri));
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
}
