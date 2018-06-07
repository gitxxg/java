package cn.ghl.swingdemo.pojo;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/2/2018
 */
public class MyRequestHeader {

    private String key;

    private String value;

    public MyRequestHeader(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
