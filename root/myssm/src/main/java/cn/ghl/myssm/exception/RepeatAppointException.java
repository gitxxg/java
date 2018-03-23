package cn.ghl.myssm.exception;

/**
 * @Author: Hailong Gong
 * @Description: 重复预约异常
 * @Date: Created in 3/20/2018
 */
public class RepeatAppointException extends RuntimeException {

    public RepeatAppointException(String message) {
        super(message);
    }

    public RepeatAppointException(String message, Throwable cause) {
        super(message, cause);
    }
}
