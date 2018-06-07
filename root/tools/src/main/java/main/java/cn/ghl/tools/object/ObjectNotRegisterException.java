package main.java.cn.ghl.tools.object;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/19/2018
 */
public class ObjectNotRegisterException extends Exception {

    private String excepiton;

    public ObjectNotRegisterException(String excepiton) {
        this.excepiton = excepiton;
    }
}
