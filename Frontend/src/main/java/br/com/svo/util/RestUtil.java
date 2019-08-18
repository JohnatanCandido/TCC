package br.com.svo.util;

import br.com.svo.util.exception.RestException;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class RestUtil {

    private static final Gson GSON = new Gson();

    private static final String urlBackend = System.getProperty("backend.url");

    public static String httpGet(String path, Map<String, String> params) {
        HttpGet httpGet = new HttpGet(createUrl(path, params));
        HttpClient client = HttpClientBuilder.create().build();
        try {
            HttpResponse response = client.execute(httpGet);
            return GSON.fromJson(EntityUtils.toString(response.getEntity()), String.class);
        } catch (IOException e) {
            return null;
        }
    }

    public static String httpPost(String path, Object param) throws RestException {
        HttpPost httpPost = new HttpPost(createUrl(path, null));
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(GSON.toJson(param, param.getClass())));
            HttpResponse response = client.execute(httpPost);
            return getResponse(response);
        } catch (IOException ignored) {
            return null;
        }
    }

    private static String getResponse(HttpResponse response) throws RestException, IOException {
        if (response.getStatusLine().getStatusCode() != 200)
            throw new RestException();
        return EntityUtils.toString(response.getEntity());
    }

    private static String createUrl(String base, Map<String, String> params) {
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
