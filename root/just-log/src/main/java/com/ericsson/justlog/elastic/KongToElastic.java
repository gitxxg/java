package com.ericsson.justlog.elastic;

import com.ericsson.justlog.utils.Log2stash;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 7/19/2018
 */
public class KongToElastic {

    private static Logger LOG = LoggerFactory.getLogger(KongToElastic.class);

    public static void main(String args[]) {
        String file = "elastic/kong-log/log.json";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            Map map = objectMapper.readValue(new ClassPathResource(file).getFile(), Map.class);
            LOG.info("{}", getJsonMap(map, "consumer.client_id"));
            setJsonMap(map, "consumer.client_id", "app-iphone");
            LOG.info("{}", getJsonMap(map, "consumer.client_id"));

            String msg;
            int method, userId, appName, apiName;
            String[] methods = {"SUB", "PUB"};
            String[] userIds = {"user-java", "user-python"};
            String[] appNames = {"app-iphone", "app-android"};
            String[] apiNames = {"api-position", "api-speed", "api-direction"};
            for (int i = 0; i < 30; i++) {
                //Log2stash.write("SUB||userId001||apiName001||");
                method = RandomUtils.nextInt(2);
                userId = RandomUtils.nextInt(2);
                appName = RandomUtils.nextInt(2);
                apiName = RandomUtils.nextInt(3);

                setJsonMap(map, "consumer.custom_id", userIds[userId]);
                setJsonMap(map, "consumer.client_id", appNames[appName]);
                setJsonMap(map, "api.name", apiNames[apiName]);
                //msg = "SUB||user-java||app-iphone||api-position||"
                msg = methods[method] + "||" + userIds[userId] + "||" + appNames[appName] + "||" + apiNames[apiName] + "||";
                LOG.info(msg);
                try {
                    int rd = RandomUtils.nextInt(5) + 3;
                    LOG.info("sleep {} s", rd);
                    Thread.sleep(rd * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getJsonMap(Map map, String path) {
        int index = path.indexOf(".");
        if (index > 0) {
            String fir = path.substring(0, index);
            String end = path.substring(index + 1);
            return getJsonMap((Map) map.get(fir), end);
        }
        return map.get(path);
    }

    public static Object setJsonMap(Map map, String path, Object val) {
        int index = path.indexOf(".");
        if (index > 0) {
            String fir = path.substring(0, index);
            String end = path.substring(index + 1);
            return setJsonMap((Map) map.get(fir), end, val);
        }
        return map.put(path, val);
    }

}
