package com.cyberpeace.oj.integratation.service.mp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClient {
    private final String domain;
    private final String clientId;
    private final String clientSecret;


    public HttpClient(String mpConfig) {
        String[] parts = mpConfig.split("\\|");
        if (parts.length != 3) {
            throw new HttpClientException("mpConfig must have three parts");
        }
        this.domain = parts[0];
        this.clientId = parts[1];
        this.clientSecret = parts[2];
    }

    public HttpClient() {
        this("http://10.96.96.240:28001|84261d495c414e|2112f4b855264ad7b1a4209737fc7836");
    }

    /**
     * 单条查询
     */
    public JSONObject get(String url) {
        url = domain + url;

        String message = request(new HttpGet(url));
        return fromJson(message);
    }

    /**
     * 列表查询
     */
    public JSONArray getList(String url) {
        url = domain + url;
        String message = request(new HttpGet(url));
        return listFromJson(message);
    }

    /**
     * post请求
     */
    public JSONObject post(String url, String body) {
        url = domain + url;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(body, "UTF-8"));
        httpPost.addHeader("contentType", "application/json");
        String message = request(httpPost);
        return fromJson(message);
    }

    /**
     * 接口响应的json字符串转换成指定类型的对象
     */
    private JSONObject fromJson(String jsonString) {
        MpModel mpModel;
        try {
            mpModel = JSONObject.parseObject(jsonString, MpModel.class);
        } catch (JSONException e) {
            throw new HttpClientException("json解析失败", e);
        }

        if (mpModel.data == null) {
            throw new HttpClientException("json解析失败");
        }

        return (JSONObject) mpModel.data;
    }

    /**
     * 接口响应的json字符串转换成指定类型对象的数组
     */
    private JSONArray listFromJson(String jsonString) {
        try {
            MpModel mpModel = JSONObject.parseObject(jsonString, MpModel.class);
            JSONObject data = (JSONObject) mpModel.data;
            return (JSONArray) data.get("list");
        } catch (JSONException e) {
            throw new HttpClientException("json解析失败", e);
        }
    }

    /**
     * 执行http请求，返回响应json字符串
     */
    private String request(HttpRequestBase httpRequest) {
        httpRequest.addHeader("clientId", clientId);
        httpRequest.addHeader("clientSecret", clientSecret);

        String requestInfo = "method: " + httpRequest.getMethod() + ", uri:" + httpRequest.getURI();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpResponse response;
        log("HttpRequest sent, " + requestInfo);
        long startTime = System.currentTimeMillis();
        try {
            response = client.execute(httpRequest);
        } catch (IOException e) {
            throw new HttpClientException("httpClient execute error", e);
        }

        long endTime = System.currentTimeMillis();
        log("HttpResponse received, " + requestInfo + ", cost " + (endTime - startTime) + "ms");

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            throw new HttpClientException("Invalid statusCode:" + statusCode);
        }

        String message;
        try {
            message = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            throw new HttpClientException("response to string error", e);
        }

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(message);
        return message;
    }

    private void log(String message) {
        System.out.println(message);
    }
}
