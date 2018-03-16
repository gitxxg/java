package cn.ghl.dubbo.service.impl;

import cn.ghl.dubbo.pojo.DubboMessage;
import cn.ghl.dubbo.service.DubboService;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 3/12/2018
 */
public class DubboServiceImpl implements DubboService {

    private static Logger LOG = LoggerFactory.getLogger(DubboServiceImpl.class);

    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    private static ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

    static {
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setMaxPoolSize(5);
        threadPoolTaskExecutor.initialize();
    }

    public Response uplink(DubboMessage message) {
        LOG.debug("--uplink-- {}", message.toString());
        return Response.ok().build();
    }

    public Response getMessage(String id) {
        LOG.debug("000-----------------------------------------");
        LOG.debug("id:{}", id);

        LOG.debug("100-----------------------------------------");
        try {
            Thread thread = new Child();
            thread.start();

            Thread th = new Thread() {
                private Map<String, String> contextMap = MDC.getCopyOfContextMap();

                @Override
                public void run() {
                    MDC.setContextMap(contextMap);
                    LOG.info("---------------------child thread-------------------");
                }
            };
            th.start();

            cachedThreadPool.execute(new RunTask("cachedThreadPool"));

            threadPoolTaskExecutor.execute(new RunTask("threadPoolTaskExecutor"));

        } catch (Exception e) {
            LOG.error("", e);
        }

        String message = "id -- " + id + " -- " + System.currentTimeMillis();
        return Response.ok().entity(message).build();
    }

    public Response getStatus(String vin, String userId, String target, String latest, String source, Long collectInterval, Long uploadInterval,
        Long startTime, Long endTime) {
        LOG.debug("{}-{}-{}", vin, userId, target);
        return Response.ok().entity("------------").build();
    }

    public void show() {
        LOG.debug("------------show()---------------------");
    }

    public class Child extends Thread {

        private Logger LOG = LoggerFactory.getLogger(Child.class);

        private Map<String, String> contextMap = MDC.getCopyOfContextMap();

        public void run() {
            MDC.setContextMap(contextMap);
            LOG.info("Running in the child thread");
        }
    }

    public class RunTask implements Runnable {

        private Map<String, String> contextMap = MDC.getCopyOfContextMap();

        private String name;

        public RunTask(String name) {
            this.name = name;
        }

        public void run() {
            MDC.setContextMap(contextMap);
            LOG.info("--------------------RunTask {}-----------------------", name);
            show();
        }
    }
}
