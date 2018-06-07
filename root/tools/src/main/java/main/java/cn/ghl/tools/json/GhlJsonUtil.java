package main.java.cn.ghl.tools.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Map;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/19/2018
 */
public class GhlJsonUtil {

    private static Gson gson = new GsonBuilder().create();

    public static String jsonToFlat(String json) {
        Map<String, Object> jsonMap = gson.fromJson(json, Map.class);
        return null;
    }

    public static String jsonMapToFlat(Map<String, Object> jsonMap) {
        return null;
    }

}
