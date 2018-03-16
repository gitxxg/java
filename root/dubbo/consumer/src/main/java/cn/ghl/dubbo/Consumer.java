package cn.ghl.dubbo;

import cn.ghl.dubbo.service.DubboService;
import com.google.gson.Gson;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 3/12/2018
 */
public class Consumer {

    private static Logger LOG = LoggerFactory.getLogger(Consumer.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-config/*.xml");
        DubboService dubboService = (DubboService) applicationContext.getBean("dubboService");
        LOG.debug("==================================");
        Response response = dubboService.getMessage("123");
        Gson gson = new Gson();
        LOG.debug("------------ consumer: {}", gson.toJson(response.getHeaders()));
    }
}
