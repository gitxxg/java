package main.java.cn.ghl.tools.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonTools {

    private static Logger LOG = LoggerFactory
            .getLogger(JsonTools.class);

    private final static Gson gson;

    private static GsonBuilder gsonBuilder = new GsonBuilder();

    private static Map<String, List<ObjectBind>> objectBindMap = new HashMap<>();

    static {
        gson = gsonBuilder.create();
        objectBindMap = MappingConfig.getObjectBindMap("/ObjectToJsonMapping");
    }

    private static Map<String, Object> objectToJsonMap(Object object, Class objClazz) {
        String className = objClazz.getName();
        Map<String, Object> jsonMap = new HashMap<>();
        // find list by class name
        List<ObjectBind> objectBindList = objectBindMap.get(className);
        for (ObjectBind objectBind : objectBindList) {
            Object objectVal = getValueFromObject(object, objectBind.getObjectPath());
            if (objectVal != null) {
                if (objectBind.isList() && objectVal instanceof List) {
                    // this is a list
                    List<Map<String, Object>> listMap = new ArrayList<>();
                    for (Object valueN : (List) objectVal) {
                        listMap.add(objectToJsonMap(valueN, valueN.getClass()));
                    }
                    jsonMap = setValueToJsonMap(jsonMap, objectBind.getJsonPath(), listMap);
                } else {
                    objectVal = objectBind.getWrite().translate(objectVal);
                    jsonMap = setValueToJsonMap(jsonMap, objectBind.getJsonPath(), objectVal);
                }
            }
        }
        return jsonMap;
    }

    public static String objectToJson(Object object, Class objClazz) {
        Map<String, Object> jsonMap = objectToJsonMap(object, objClazz);
        return gson.toJson(jsonMap, HashMap.class);
    }

    private static Map<String, Object> setValueToJsonMap(Map<String, Object> jsonMap, String jsonPath, Object val) {
        if (jsonMap == null || StringUtils.isBlank(jsonPath)) {
            return jsonMap;
        }
        String fields[] = jsonPath.split("\\.");
        if (fields.length > 1) {
            Map<String, Object> subMap = (Map<String, Object>) jsonMap.get(fields[0]);
            if (subMap == null) {
                subMap = new HashMap();
            }
            subMap = setValueToJsonMap(subMap, jsonPath.substring(fields[0].length() + 1), val);
            jsonMap.put(fields[0], subMap);
        } else {
            jsonMap.put(jsonPath, val);
        }
        return jsonMap;
    }

    private static Object getValueFromObject(Object object, String objectPath) {
        try {
            if (object == null || StringUtils.isBlank(objectPath)) {
                return object;
            }
            String[] paths = objectPath.split("\\.");
            Object subObj = object;
            for (int size = 0; size < paths.length; size++) {
                subObj = PropertyUtils.getProperty(subObj, paths[size]);
                if (subObj == null) {
                    return object;
                }
            }
            return subObj;
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }


    private static Object setValueToObject(Object object, String objectPath, Object value) {
        if (object == null || StringUtils.isBlank(objectPath)) {
            return object;
        }
        try {
            String[] paths = objectPath.split("\\.");
            Object subObj = object;
            int deep = 0;
            for (; deep < paths.length - 1; deep++) {
                Object propValue = PropertyUtils.getProperty(subObj, paths[deep]);
                if (propValue == null) {
                    Class propType = PropertyUtils.getPropertyType(subObj, paths[deep]);
                    propValue = propType.newInstance();
                    PropertyUtils.setProperty(subObj, paths[deep], propValue);
                }
                subObj = propValue;
            }
            PropertyUtils.setProperty(subObj, paths[deep], value);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return object;
    }


    private static Object getValueFromJsonMap(Map<String, Object> jsonMap, String jsonPath) {
        if (jsonMap == null || StringUtils.isBlank(jsonPath)) {
            return jsonMap;
        }
        String[] paths = jsonPath.split("\\.");
        Object obj = jsonMap;
        for (int deep = 0; deep < paths.length; deep++) {
            obj = ((Map<String, Object> ) obj).get(paths[deep]);
        }
        return obj;
    }

    public static Object jsonToObject(String json, Class clazz) {
        try {
            Map<String, Object> jsonMap = gson.fromJson(json, HashMap.class);
            return jsonMapToObject(jsonMap, clazz);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }

    /**
     * 将json map转化为一个java object
     *
     * @param jsonMap
     * @param clazz
     * @return
     */
    private static Object jsonMapToObject(Map<String, Object> jsonMap, Class clazz) {
        try {
            // 通过类名生成对象实例
            Object object = clazz.newInstance();
            // 通过类名获取到映射规则
            List<ObjectBind> objectBindList = objectBindMap.get(clazz.getName());
            for (ObjectBind objectBind : objectBindList) {
                // 获取到json节点的值
                Object jsonValue = getValueFromJsonMap(jsonMap, objectBind.getJsonPath());
                if (jsonValue != null) {
                    if (objectBind.isList() && jsonValue instanceof List) {
                        // 如果该json节点是一个array节点，需要遍历
                        // 将json array转发为对应的java list
                        List<Map<String, Object>> jsonMapList = (List<Map<String, Object>>) jsonValue;
                        // 获取list的具体对象
                        Class subClazz = Class.forName(objectBind.getListObjectType());
                        List<Object> objectList = new ArrayList();
                        for (int i = 0; i < jsonMapList.size(); i++) {
                            objectList.add(jsonMapToObject(jsonMapList.get(i), subClazz));
                        }
                        // 将java list设置给object对象
                        setValueToObject(object, objectBind.getObjectPath(), objectList);
                    } else {
                        // 普通节点
                        // 值转换，然后设置给object对象
                        jsonValue = objectBind.getRead().translate(jsonValue);
                        setValueToObject(object, objectBind.getObjectPath(), jsonValue);
                    }
                }
            }
            return object;
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }

}
