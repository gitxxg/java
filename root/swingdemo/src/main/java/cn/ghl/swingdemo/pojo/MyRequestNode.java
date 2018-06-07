package cn.ghl.swingdemo.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyRequestNode implements Serializable {

    private String name;

    private String url;

    private Integer timeout;

    private String jsonParam;

    private MyRequestMethod method;

    private List<MyRequestHeader> headers;

    public MyRequestNode(String name) {

        this.name = name;

        this.timeout = 2000;

        this.method = MyRequestMethod.GET;

        this.headers = new ArrayList<MyRequestHeader>();
        this.headers.add(new MyRequestHeader("Content-Type", "application/json"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getJsonParam() {
        return jsonParam;
    }

    public void setJsonParam(String jsonParam) {
        this.jsonParam = jsonParam;
    }

    public MyRequestMethod getMethod() {
        return method;
    }

    public void setMethod(MyRequestMethod method) {
        this.method = method;
    }

    public List<MyRequestHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<MyRequestHeader> headers) {
        this.headers = headers;
    }
}
