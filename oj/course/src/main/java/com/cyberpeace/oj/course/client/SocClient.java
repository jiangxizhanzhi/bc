package com.cyberpeace.oj.course.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.WebConstant;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class SocClient {


    public CloseableHttpClient httpClient;


    private SocClient() throws Exception{
        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial((chain, authType) -> true)
                .build();
        this.httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();
    }


    public String sendRequestToSocServer(JSONObject message, String url, String urlType, Map<String, String> headers) throws Exception{
        log.info("sendMessage: {}", Objects.nonNull(message)?message.toJSONString():"");
        if (urlType == null || url == null) {
            log.warn("urlType 或 url 参数为空");
            return StringUtils.EMPTY;
        }
        HttpRequestBase request = createRequest(message, url, urlType, headers);
        if (request == null) {
            throw new Exception("不支持的请求类型: " + urlType);
        }
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("响应状态码: {}", statusCode);
            // 获取响应体内容
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            log.info("response result: {}", result);
            return result;
        } catch (Exception e) {
            log.error("请求失败 - URL: {}, 类型: {}", url, urlType, e);
        }
        return StringUtils.EMPTY;
    }


    /**
     * 创建http请求
     * @param message
     * @param url
     * @param urlType
     * @param headers
     * @return
     * @throws Exception
     */
    private HttpRequestBase createRequest(JSONObject message, String url, String urlType, Map<String,String> headers){
        if (WebConstant.POST.equalsIgnoreCase(urlType)) {
            return createPostRequest(message, url, headers);
        } else if (WebConstant.GET.equalsIgnoreCase(urlType)) {
            return createGetRequest(url, headers);
        }
        return null;
    }

    private HttpRequestBase createPostRequest(JSONObject message, String url, Map<String,String> headers){
        HttpPost post = new HttpPost(url);
        // 设置Content-Type为application/json并指定编码为UTF-8
        post.setHeader("Content-Type", "application/json; charset=UTF-8");
        if(MapUtils.isNotEmpty(headers)){
            headers.forEach((key,val)->{
                post.addHeader(key,val);
            });
        }
        // 使用UTF-8编码将message写入请求体
        if(Objects.nonNull(message)){
            post.setEntity(new StringEntity(message.toString(), StandardCharsets.UTF_8));
        }
        return post;
    }

    private HttpRequestBase createGetRequest(String url, Map<String,String> headers){
        HttpGet httpGet = new HttpGet(url);
        if(MapUtils.isNotEmpty(headers)){
            headers.forEach((key,val)->{
                httpGet.addHeader(key,val);
            });
        }
        return httpGet;
    }
}
