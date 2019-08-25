package br.com.svo.util;

import br.com.svo.entities.Identity;
import br.com.svo.util.exception.RestException;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestUtil {

    private static final Gson GSON = new Gson();

    @Inject
    private Identity identity;

    private static final String urlBackend = System.getProperty("backend.url");

    public String httpGet(String path, Map<String, String> params) throws RestException {
        HttpGet httpGet = new HttpGet(createUrl(path, params));
        httpGet.setHeader("Authorization", identity.getToken());
        HttpClient client = HttpClientBuilder.create().build();
        try {
            HttpResponse response = client.execute(httpGet);
            return getResponse(response);
        } catch (IOException e) {
            return null;
        }
    }

    public String httpPost(String path, Object param) throws RestException {
        HttpPost httpPost = new HttpPost(createUrl(path, null));
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            setHeaders(httpPost);
            httpPost.setEntity(new StringEntity(GSON.toJson(param, param.getClass()), "UTF-8"));
            HttpResponse response = client.execute(httpPost);
            return getResponse(response);
        } catch (IOException ignored) {
            return null;
        }
    }

    private void setHeaders(HttpPost httpPost) {
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", identity.getToken());
    }

    private String getResponse(HttpResponse response) throws RestException, IOException {
        if (response.getStatusLine().getStatusCode() == 200)
            return EntityUtils.toString(response.getEntity());
        throw new RestException(GSON.fromJson(EntityUtils.toString(response.getEntity()), List.class));
    }

    private String createUrl(String base, Map<String, String> params) {
        if (params == null || params.isEmpty())
            return urlBackend + base;

        List<String> urlParams = new ArrayList<>();
        for (Map.Entry entry: params.entrySet()) {
            urlParams.add(entry.getKey() + "=" + entry.getValue());
        }

        String formattedParams = String.join("&", urlParams);

        return urlBackend + base + "?" + formattedParams;
    }
}
