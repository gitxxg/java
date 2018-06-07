package main.java.cn.ghl.tools.http;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.message.BasicNameValuePair;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/11/2018
 */
public class HttClientUseDemo extends HttpClientService {

    public static void main(String[] args) {

        new HttClientUseDemo().getConfCall();
    }

    public void getConfCall() {

        String url = "http://127.0.0.1:28080/tds/generic/v1/vehicles/VIN123/journey/";

        List<BasicNameValuePair> urlParams = new ArrayList<>();
        urlParams.add(new BasicNameValuePair("since", "1513228659000"));
        urlParams.add(new BasicNameValuePair("until", "1513228659005"));
        urlParams.add(new BasicNameValuePair("offset", "0"));
        urlParams.add(new BasicNameValuePair("limit", "10"));
        exeHttpReq(url, false, urlParams, null, new GetConfCall());
    }

    public void exeHttpReq(String baseUrl, boolean isPost,
        List<BasicNameValuePair> urlParams,
        List<BasicNameValuePair> postBody,
        FutureCallback<HttpResponse> callback) {

        try {
            System.out.println("enter exeAsyncReq");
            exeAsyncReq(baseUrl, isPost, urlParams, postBody, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 被回调的对象，给异步的httpclient使用
     */
    class GetConfCall implements FutureCallback<HttpResponse> {

        /**
         * 请求完成后调用该函数
         */
        @Override
        public void completed(HttpResponse response) {

            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println(getHttpContent(response));

            HttpClientUtils.closeQuietly(response);

        }

        /**
         * 请求取消后调用该函数
         */
        @Override
        public void cancelled() {

        }

        /**
         * 请求失败后调用该函数
         */
        @Override
        public void failed(Exception e) {

        }

    }
}
