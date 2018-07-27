package com.ericsson.justlog;

import com.ericsson.justlog.utils.Log2stash;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 7/10/2018
 */
public class JustLog {

    private static Logger LOG = LoggerFactory.getLogger(JustLog.class);

    public static void main(String args[]) {
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
            //msg = "SUB||user-java||app-iphone||api-position||"
            msg = methods[method] + "||" + userIds[userId] + "||" + appNames[appName] + "||" + apiNames[apiName] + "||";
            LOG.info(msg);
            Log2stash.write(msg);
            try {
                int rd = RandomUtils.nextInt(5) + 3;
                LOG.info("sleep {} s", rd);
                Thread.sleep(rd * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
