package main.java.cn.ghl.tools.http;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/11/2018
 */
public class HttpClientUtilTest {

    private static Logger LOG = LoggerFactory
        .getLogger(HttpClientUtilTest.class);

    private AsyncHttpClient asyncClient = new DefaultAsyncHttpClient();

    private int thread = 50;

    private CountDownLatch latch = new CountDownLatch(thread);

    @Test
    public void testDoGet() {
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < thread; i++) {
            new Thread(new Runner()).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Long endTime = System.currentTimeMillis();
        LOG.debug("{}", endTime - startTime);
    }

    class Runner implements Runnable {

        @Override
        public void run() {
            String uri = "http://127.0.0.1:28080/tds/generic/v1/vehicles/VIN123/journey/?since=1513228659000&until=1513228659005&limit=10&offset=0";
            ListenableFuture<Response> response = asyncClient.prepareGet(uri)
                .setHeader("Content-Type", "application/json;charset=UTF-8")
                .execute();
            if (response != null) {
                try {
                    Response res = response.get();
                    //LOG.debug("1 - {}", res.getResponseBody());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            latch.countDown(); // 执行完毕，计数器减1
        }
    }
}
