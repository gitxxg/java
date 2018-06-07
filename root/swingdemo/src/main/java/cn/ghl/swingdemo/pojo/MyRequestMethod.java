package cn.ghl.swingdemo.pojo;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/2/2018
 */
public enum MyRequestMethod {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private String code;

    MyRequestMethod(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static MyRequestMethod findByCode(String code) {
        for (MyRequestMethod method : MyRequestMethod.values()) {
            if (method.code.equalsIgnoreCase(code)) {
                return method;
            }
        }
        return null;
    }
}
