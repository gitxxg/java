package cn.ghl.stock.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 7/20/2018
 */
public class JsonMapUtils {

    public static final ObjectMapper objectMapper = new ObjectMapper();

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
