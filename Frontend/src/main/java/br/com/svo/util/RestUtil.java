package br.com.svo.util;

import br.com.svo.business.exception.NoResultException;
import br.com.svo.util.exception.RestException;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestUtil {

    private static final Gson GSON = new Gson();

    private static final String urlBackend = System.getProperty("backend.url");
    private String path;
    private List<String> params = new ArrayList<>();
    private Map<String, String> headers = new HashMap<>();
    private Object body;

    public RestUtil(String path) {
        this.path = urlBackend + path;
    }

//    public RestUtil withParam(String key, String value) {
//        params.add(key + "=" + value);
//        return this;
//    }

    public RestUtil withBody(Object body) {
        this.body = body;
        return this;
    }

    public RestUtil withHeader(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public String get() throws RestException, NoResultException {
        HttpGet httpGet = new HttpGet(createUrl());
        setHeaders(httpGet);
        setBody(httpGet);

        HttpClient client = HttpClientBuilder.create().build();
        try {
            HttpResponse response = client.execute(httpGet);
            return getResponse(response);
        } catch (IOException e) {
            throw new RestException("Erro ao conectar com o servidor");
        }
    }

    public String post() throws RestException, NoResultException {
        HttpPost httpPost = new HttpPost(createUrl());
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            setHeaders(httpPost);
            setBody(httpPost);
            HttpResponse response = client.execute(httpPost);
            return getResponse(response);
        } catch (IOException ignored) {
            throw new RestException("Erro ao conectar com o servidor");
        }
    }

    private void setBody(HttpEntityEnclosingRequestBase request) {
        if (body != null)
            request.setEntity(new StringEntity(GSON.toJson(body, body.getClass()), "UTF-8"));
    }

    private void setHeaders(HttpRequestBase request) {
        for (Map.Entry<String, String> header: headers.entrySet()) {
            request.addHeader(header.getKey(), header.getValue());
        }
    }

    private String getResponse(HttpResponse response) throws RestException, IOException, NoResultException {
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200)
            return EntityUtils.toString(response.getEntity());
        if (statusCode == 204)
            throw new NoResultException("Nenhum registro encontrado");
        throw new RestException(GSON.fromJson(EntityUtils.toString(response.getEntity()), List.class));
    }

    private String createUrl() {
        if (params.isEmpty())
            return path;

        String formattedParams = String.join("&", params);

        return path + "?" + formattedParams;
    }
}
