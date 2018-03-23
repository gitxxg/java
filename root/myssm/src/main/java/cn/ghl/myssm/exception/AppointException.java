package cn.ghl.myssm.exception;

/**
 * @Author: Hailong Gong
 * @Description: 预约业务异常
 * @Date: Created in 3/20/2018
 */
public class AppointException extends RuntimeException {

    public AppointException(String message) {
        super(message);
    }

    public AppointException(String message, Throwable cause) {
        super(message, cause);
    }
}
