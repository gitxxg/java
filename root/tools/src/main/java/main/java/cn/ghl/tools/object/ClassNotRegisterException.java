package main.java.cn.ghl.tools.object;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/19/2018
 */
public class ClassNotRegisterException extends Exception {

    public ClassNotRegisterException(Class clazz) {
        super(clazz + " is not register!");
    }
}
