package main.java.cn.ghl.tools.http;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/11/2018
 */
public class HttpClientFactory {

    private static HttpAsyncClient httpAsyncClient = new HttpAsyncClient();

    private static HttpSyncClient httpSyncClient = new HttpSyncClient();

    private HttpClientFactory() {
    }

    private static HttpClientFactory httpClientFactory = new HttpClientFactory();

    public static HttpClientFactory getInstance() {

        return httpClientFactory;

    }

    public HttpAsyncClient getHttpAsyncClientPool() {
        return httpAsyncClient;
    }

    public HttpSyncClient getHttpSyncClientPool() {
        return httpSyncClient;
    }
}
