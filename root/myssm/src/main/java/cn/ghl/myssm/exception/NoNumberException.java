package cn.ghl.myssm.exception;

/**
 * @Author: Hailong Gong
 * @Description: 库存不足异常
 * @Date: Created in 3/20/2018
 */
public class NoNumberException extends RuntimeException {

    public NoNumberException(String message) {
        super(message);
    }

    public NoNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}
