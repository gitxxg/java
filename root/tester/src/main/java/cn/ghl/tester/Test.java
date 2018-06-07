package cn.ghl.tester;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/10/2018
 */
public class Test {

    private static Logger LOG = LoggerFactory.getLogger(Test.class);

    private String name;

    private Long expire = 2L;

    private String unit = "DAYS";

    public Test() {
        System.out.println("Test(){}");
    }

    public Test(Long expire, String unit) {
        System.out.println("Test(){}");
        this.expire = expire;
        this.unit = unit;
        init();
    }

    {
        System.out.println("static{}");
        System.out.println("name = " + name);
    }

    public static void main(String args[]) {
        Integer latitude = 83192695;
        System.out.println("车辆上报： latitude = " + latitude);
        Double save = latitude / 3600000D;
        System.out.println("保存到tds：" + latitude + " / 3600000 = " + save);
        System.out.println("保存到tds：" + latitude + " / 3600000 = " + latitude / 3600000);
        System.out.println("保存到tds：" + latitude + " / 3600000 = " + new Double(latitude) / 3600000);
        System.out.println("保存到tds：" + latitude + " / 3600000 = " + new Double(latitude / 3600000D));
        Double read = save * 3600000L;
        latitude = read.intValue();
        System.out.println("从tds读取： " + save + " * 3600000 = " + latitude);
        //System.out.println(latitude + " * 1.0 = " + (latitude * 1.0));
    }

    public static void test1() {
        Test test = new Test(0L, "SECONDS");
        test.setName("123name");
        test.setUnit("SECONDS");
        test.setExpire(2L);

        System.out.println("end main.............");

        RuntimeException e = new RuntimeException("123");
        LOG.error("error", e);

        RuntimeException runtimeException = new MyException("123", null, false, false);
        LOG.error("error", runtimeException);

        RuntimeException e1 = new RuntimeException("123");
        LOG.error("error", e1);

        try {
            test.testE();
        } catch (RuntimeException e2) {
            LOG.error("-----", e2);
        }
    }

    public void testE() throws MyException {
        RuntimeException runtimeException = new MyException("123", null, false, false);
        throw runtimeException;
    }

    public static class MyException extends RuntimeException {

        public MyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }

    public void init() {
        System.out.println("-------getExpire() : " + getExpire());
        System.out.println("-------getUnit() : " + getUnit());
        if (getExpire() <= 0) {
            return;
        }
        Long expire = getExpire();
        TimeUnit timeUnit = TimeUnit.valueOf(getUnit());
        final long expireMill = timeUnit.toMillis(expire);
        final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long systemTime = System.currentTimeMillis();
                System.out.println("------- : " + systemTime / 1000);
            }
        }, expire, expire, timeUnit);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
