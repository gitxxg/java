package cn.ghl.web.tools;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 6/8/2018
 */
@Service
public class WebContextClient {

    private static final Logger logger = LoggerFactory.getLogger(WebContextClient.class);

    public String getWebContext(String uri) {
        return getWebContext(uri, "utf-8");
    }

    public String getWebContext(String uri, String defaultCharset) {

        CloseableHttpClient httpClient = null;
        try {

            logger.info("GET {}", uri);

            httpClient = HttpClients.createDefault();
            //发送get请求
            HttpGet httpget = new HttpGet(uri);
            //设置超时时间
            RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000).build();
            // 伪装成浏览器
            httpget.addHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36");
            httpget.setConfig(config);

            HttpResponse response = httpClient.execute(httpget);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity(), defaultCharset);

                return strResult;
            }
        } catch (IOException e) {
            logger.error("{}", e);
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    logger.error("{}", e);
                }
            }
        }

        return null;
    }
}
