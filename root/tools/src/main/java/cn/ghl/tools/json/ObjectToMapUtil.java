package cn.ghl.tools.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectToMapUtil {

    private static Logger LOG = LoggerFactory
            .getLogger(ObjectToMapUtil.class);


    public static Map<String, Object> toMap(Object object) {
        Gson gson = new GsonBuilder().create();
        try {
            Map<String, Object> map = PropertyUtils.describe(object);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object val = entry.getValue();
                if ("class".equals(key)) {
                    continue;
                }
                LOG.debug("-----------------------");
                if (val instanceof List) {
                    List<Object> valList = (List) val;
                    for (int i = 0; i < valList.size() - 1; i++) {
                        toMap(valList.get(i));
                    }
                } else {
                    LOG.debug("{} - {}", key, val);
                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> toDeepMap(Object object) {
        Gson gson = new GsonBuilder().create();
        try {
            Map<String, Object> deepMap = new HashMap<>();
            Map<String, Object> map = PropertyUtils.describe(object);
            map.forEach((key, val) -> {
                if ("class".equals(key)) {
                    // do nothing
                } else {
                    if (val instanceof List) {
                        List<Object> valList = (List) val;
                        for (int i = 0; i < valList.size() - 1; i++) {
                            Map<String, Object> valMap = toDeepMap(valList.get(i));
                            int deep = i;
                            valMap.forEach((key2, val2) -> {
                                deepMap.put(key + "[" + deep + "]." + key2, val2);
                            });
                        }
                    } else {
                        deepMap.put(key, val);
                    }
                }
            });
            return deepMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
