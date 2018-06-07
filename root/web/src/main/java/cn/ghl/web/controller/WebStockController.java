package cn.ghl.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 6/7/2018
 */
@RestController
public class WebStockController {

    private static final Logger logger = LoggerFactory.getLogger(WebStockController.class);

    private static final String FORMAT = "%s|";

    public static void main(String[] args) throws IOException {
        WebStockController webStockController = new WebStockController();
        String url = null;
        // 主要财务指标按报告期
        url = "http://emweb.securities.eastmoney.com/NewFinanceAnalysis/MainTargetAjax?ctype=4&type=0&code=sz000651";
        // 主要财务指标按年度
        url = "http://emweb.securities.eastmoney.com/NewFinanceAnalysis/MainTargetAjax?ctype=4&type=1&code=sz000651";
        String html = webStockController.getWebContext(url);
        logger.info("json：{}", html);
        List<Map<String, String>> list = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        list = gson.fromJson(html, list.getClass());
        logger.info("list：{}", list);
        Map<String, String> target = webStockController.getTargetMap();

        for (String key : target.keySet()) {
            StringBuffer date = new StringBuffer();
            date.append(String.format(FORMAT, target.get(key)));
            for (Map<String, String> map : list) {
                date.append(String.format(FORMAT, map.get(key)));
            }
            System.out.println(date.toString());
        }
    }

    @RequestMapping(value = "/web/stock/context", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getWebContext(@RequestParam(required = true) String uri) {
        try {
            HttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet httpget = new HttpGet(uri);
            //设置代理IP
            HttpHost proxy = new HttpHost("127.0.0.1", 80);
            //设置超时时间
            RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(5000).setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000).build();
            // 伪装成浏览器
            httpget.addHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36");

            httpget.setConfig(config);
            HttpResponse response = client.execute(httpget);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity(), "utf-8");

                return strResult;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Map<String, String> getTargetMap() {
        Map<String, String> target = new HashMap<>();
        target.put("date", "指标\\日期");
        target.put("jbmgsy", "基本每股收益(元)");
        target.put("yyzsr", "营业总收入");
        return target;
    }
}
