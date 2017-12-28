package cn.ghl.utils;

import cn.ghl.pojo.MyHttpRequest;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestUtils {


    private static Logger LOG = LoggerFactory.getLogger(HttpRequestUtils.class);    //日志记录

    public static String httpPost(MyHttpRequest request) {
        String url = request.getUrl();
        Map<String, String> headers = request.getHeaders();
        int timeout = request.getTimeout();
        String jsonParam = request.getJsonParam();

        if (StringUtils.isBlank(url)) {
            return null;
        }

        RequestConfig defaultRequestConfig = RequestConfig.custom()
            .setSocketTimeout(timeout)
            .setConnectTimeout(timeout)
            .setConnectionRequestTimeout(timeout)
            .build();

        if (StringUtils.isBlank(url)) {
            return null;
        }
        //post请求返回结果
        CloseableHttpClient httpClient = HttpClients.custom()
            .setDefaultRequestConfig(defaultRequestConfig).build();
        String jsonResult = null;
        HttpPost postMethod = new HttpPost(url);
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                postMethod.setEntity(entity);
            }
            // 添加http头
            for (Map.Entry<String, String> header : headers.entrySet()) {
                postMethod.addHeader(header.getKey(), header.getValue());
            }
            HttpResponse response = httpClient.execute(postMethod);
            if (response != null) {
                return simpleHttpResponse(response);
            }
        } catch (Exception e) {
            LOG.error("Exception ", e);
            return e.getMessage();
        }
        return jsonResult;
    }


    public static String httpGet(MyHttpRequest request) {
        String url = request.getUrl();
        Map<String, String> headers = request.getHeaders();
        int timeout = request.getTimeout();

        if (StringUtils.isBlank(url)) {
            return null;
        }

        RequestConfig defaultRequestConfig = RequestConfig.custom()
            .setSocketTimeout(timeout)
            .setConnectTimeout(timeout)
            .setConnectionRequestTimeout(timeout)
            .build();

        //get请求返回结果
        String jsonResult = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig).build();
            //发送get请求
            HttpGet getMethod = new HttpGet(url);
            // 添加http头
            for (Map.Entry<String, String> header : headers.entrySet()) {
                getMethod.addHeader(header.getKey(), header.getValue());
            }
            HttpResponse response = httpClient.execute(getMethod);
            if (response != null) {
                return simpleHttpResponse(response);
            }
        } catch (Exception e) {
            LOG.error("Exception ", e);
            return e.getMessage();
        }
        return jsonResult;
    }

    public static String simpleHttpResponse(HttpResponse httpResponse) {
        if (httpResponse == null) {
            return null;
        }
        try {
            int code = httpResponse.getStatusLine().getStatusCode();
            String result = EntityUtils.toString(httpResponse.getEntity());
            return "code: " + code + "\nresult:\n" + PrettyJson.format(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
