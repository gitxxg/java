package cn.ghl.dubbo.service.impl;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 3/12/2018
 */
public class ThreadAny extends Thread {

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DubboServiceImpl.class);

    private static Logger LOG = LoggerFactory.getLogger(ThreadAny.class);

    // contextMap is set when new Child() is called
    private Map<String,String> contextMap = MDC.getCopyOfContextMap();

    public void run() {
        MDC.setContextMap(contextMap);  // set contextMap when thread start
        LOG.debug("--- ThreadAny --- {}", Thread.currentThread().getName());
        logger.debug("===logger=== " + Thread.currentThread().getName());
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
