package main.java.cn.ghl.tools.http;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/11/2018
 */
public class HttpClientUtil2Test {

    private static Logger LOG = LoggerFactory
        .getLogger(HttpClientUtil2Test.class);

    private CloseableHttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(1000)
        .setMaxConnPerRoute(1000).build();


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
            HttpGet httpget = new HttpGet(uri);
            httpget.setHeader("Content-Type", "application/json;charset=UTF-8");
            try {
                HttpResponse httpResponse = httpClient.execute(httpget);
                //LOG.debug("2 - {}", EntityUtils.toString(httpResponse.getEntity()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            latch.countDown(); // 执行完毕，计数器减1
        }

    }
}
