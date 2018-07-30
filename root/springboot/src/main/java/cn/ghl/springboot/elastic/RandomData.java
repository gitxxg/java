package cn.ghl.springboot.elastic;

import org.apache.commons.lang.math.RandomUtils;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 7/20/2018
 */
public class RandomData {

    private static String[] methods = {"SUB", "PUB"};

    private static String[] userIds = {"user-java", "user-python"};

    private static String[] appNames = {"app-iphone", "app-android"};

    private static String[] apiNames = {"api-position", "api-speed", "api-direction", "api-position_1.0.1", "api-speed_1.0.0", "api-speed_2.0.0"};

    private static Integer[] statuss = {200, 500};

    private static String timestamps = "2018-05-12T%02d:%02d:%02d.%03dZ";

    public static String getMethod() {
        return methods[RandomUtils.nextInt(methods.length)];
    }

    public static String getUserId() {
        return userIds[RandomUtils.nextInt(userIds.length)];
    }

    public static String getAppName() {
        return appNames[RandomUtils.nextInt(appNames.length)];
    }

    public static String getApiName() {
        return apiNames[RandomUtils.nextInt(apiNames.length)];
    }

    public static Integer getStatus() {
        return statuss[RandomUtils.nextInt(statuss.length)];
    }

    public static String getTimestamp() {
        int hh = RandomUtils.nextInt(23);
        int mm = RandomUtils.nextInt(59);
        int ss = RandomUtils.nextInt(59);
        int SSS = RandomUtils.nextInt(999);
        return String.format(timestamps, hh, mm, ss, SSS);
    }
}
