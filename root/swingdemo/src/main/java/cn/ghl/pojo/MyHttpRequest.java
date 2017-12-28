package cn.ghl.pojo;

import java.io.Serializable;
import java.util.Map;

public class MyHttpRequest implements Serializable {

    private String url;

    private Map<String, String> headers;

    private int timeout = 2000;

    private String jsonParam;

    private String method;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getJsonParam() {
        return jsonParam;
    }

    public void setJsonParam(String jsonParam) {
        this.jsonParam = jsonParam;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "MyHttpRequest{" +
            "url='" + url + '\'' +
            ", headers=" + headers +
            ", timeout=" + timeout +
            ", jsonParam='" + jsonParam + '\'' +
            ", method='" + method + '\'' +
            '}';
    }
}
