package cn.ghl.swingdemo.utils;

import cn.ghl.swingdemo.pojo.MyRequestHeader;
import cn.ghl.swingdemo.pojo.MyRequestNode;
import java.io.IOException;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
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

public class HttpRequestUtil {

    //日志记录
    private static Logger LOG = LoggerFactory.getLogger(HttpRequestUtil.class);

    public static String httpPost(MyRequestNode request) {
        String url = request.getUrl();
        List<MyRequestHeader> headers = request.getHeaders();
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
        HttpResponse response = null;

        try {
            HttpPost postMethod = new HttpPost(url);
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString());
                //entity.setContentEncoding("UTF-8");
                postMethod.setEntity(entity);
            }
            // 添加http头
            if (headers != null) {
                for (MyRequestHeader header : headers) {
                    postMethod.addHeader(header.getKey(), header.getValue());
                }
            }

            long startTime = System.currentTimeMillis();
            response = httpClient.execute(postMethod);
            long endTime = System.currentTimeMillis();
            if (response != null) {
                return simpleHttpResponse(response, endTime - startTime);
            }
        } catch (Exception e) {
            LOG.error("Exception ", e);
            return e.getMessage();
        } finally {
            if (response != null) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    try {
                        EntityUtils.consume(entity);
                    } catch (IOException ioe) {
                        LOG.error("Exception ", ioe);
                    }
                }
            }
        }
        return null;
    }


    public static String httpGet(MyRequestNode request) {
        String url = request.getUrl();
        List<MyRequestHeader> headers = request.getHeaders();
        int timeout = request.getTimeout();

        if (StringUtils.isBlank(url)) {
            return null;
        }

        RequestConfig defaultRequestConfig = RequestConfig.custom()
            .setSocketTimeout(timeout)
            .setConnectTimeout(timeout)
            .setConnectionRequestTimeout(timeout)
            .build();
        HttpResponse response = null;

        try {
            CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig).build();
            //发送get请求
            HttpGet getMethod = new HttpGet(url);
            // 添加http头
            if (headers != null) {
                for (MyRequestHeader header : headers) {
                    getMethod.addHeader(header.getKey(), header.getValue());
                }
            }

            long startTime = System.currentTimeMillis();
            response = httpClient.execute(getMethod);
            long endTime = System.currentTimeMillis();
            if (response != null) {
                return simpleHttpResponse(response, endTime - startTime);
            }
        } catch (Exception e) {
            LOG.error("Exception ", e);
            return e.getMessage();
        } finally {
            if (response != null) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    try {
                        EntityUtils.consume(entity);
                    } catch (IOException ioe) {
                        LOG.error("Exception ", ioe);
                    }
                }
            }
        }
        return null;
    }

    public static String simpleHttpResponse(HttpResponse httpResponse, long runTime) {
        if (httpResponse == null) {
            return null;
        }
        try {
            int code = httpResponse.getStatusLine().getStatusCode();
            String result = EntityUtils.toString(httpResponse.getEntity());
            return "time: " + runTime + " ms\n"
                + "code: " + code + "\n"
                + "result: \n" + PrettyJsonUtil
                .format(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
