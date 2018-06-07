package main.java.cn.ghl.tools.object;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class GhlObjectUtil {

    private static Logger LOG = LoggerFactory
        .getLogger(GhlObjectUtil.class);

    /**
     *
     * @param object
     * @param objectPath
     * @return
     */
    public static Object getProperty(Object object, String objectPath) {
        if (object == null || StringUtils.isBlank(objectPath)) {
            return null;
        }
        try {
            if (object instanceof List) {
                List<Object> retObjectList = new ArrayList<>();
                for (Object subObject : (List) object) {
                    retObjectList.add(getProperty(subObject, objectPath));
                }
                return retObjectList;
            } else {
                Object retObject;
                int index = objectPath.indexOf(".");
                if (index > 0) {
                    String currObjectPath = objectPath.substring(0, index);
                    String subObjectPath = objectPath.substring(index + 1);
                    Object currObject = getSimpleProperty(object, currObjectPath);
                    retObject = getProperty(currObject, subObjectPath);
                } else {
                    retObject = getSimpleProperty(object, objectPath);
                }
                return retObject;
            }
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }

    /**
     *
     * @param object
     * @param property
     * @return
     */
    public static Object getSimpleProperty(Object object, String property) {
        if (object == null || StringUtils.isBlank(property)) {
            return null;
        }
        try {
            Class clazz = object.getClass();
            String getProperty = "get" + StringUtils.capitalize(property);
            Method getMethod = MethodUtils
                .getMatchingAccessibleMethod(clazz, getProperty, null);
            Object targetObject = getMethod.invoke(object, null);
            return targetObject;
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }

    /**
     *
     * @param object
     * @param objectPath
     * @param value
     */
    public static void setProperty(Object object, String objectPath, Object value) {
        if (object == null || StringUtils.isBlank(objectPath)) {
            return;
        }
        try {
            if (object instanceof List) {
                for (Object subObject : (List) object) {
                    //LOG.debug("This is a list...");
                    setProperty(subObject, objectPath, value);
                }
            } else {
                int index = objectPath.indexOf(".");
                if (index > 0) {
                    String currObjectPath = objectPath.substring(0, index);
                    String subObjectPath = objectPath.substring(index + 1);
                    Object currObject = getSimpleProperty(object, currObjectPath);
                    setProperty(currObject, subObjectPath, value);
                } else {
                    setSimpleProperty(object, objectPath, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    /**
     *
     * @param object
     * @param property
     * @param value
     * @return
     */
    public static Object setSimpleProperty(Object object, String property, Object value) {
        if (object == null || StringUtils.isBlank(property)) {
            return null;
        }
        try {
            Class clazz = object.getClass();
            String setProperty = "set" + StringUtils.capitalize(property);
            Class propertyType = PropertyUtils.getPropertyType(object, property);
            Method setMethod = MethodUtils
                .getMatchingAccessibleMethod(clazz, setProperty, new Class[]{propertyType});
            Object targetObject = setMethod.invoke(object, value);
            return targetObject;
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }

    /**
     *
     * @param object
     * @param objectPath
     */
    public static void parseProperty(Object object, String objectPath) {
        if (object == null || StringUtils.isBlank(objectPath)) {
            return;
        }
        try {
            if (object instanceof List) {
                for (Object subObject : (List) object) {
                    //LOG.debug("This is a list...");
                    parseProperty(subObject, objectPath);
                }
            } else {
                int index = objectPath.indexOf(".");
                if (index > 0) {
                    String currObjectPath = objectPath.substring(0, index);
                    String subObjectPath = objectPath.substring(index + 1);
                    Object currObject = getSimpleProperty(object, currObjectPath);
                    parseProperty(currObject, subObjectPath);
                } else {
                    Object retObject = getSimpleProperty(object, objectPath);
                    if (retObject instanceof Integer) {
                        retObject = ((Integer) retObject).intValue() + 100000;
                        setSimpleProperty(object, objectPath, retObject);
                    }
                    LOG.debug("{} : {} : {}", object, objectPath, retObject);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public static void parseProperty(Object object, String objectPath,
        GhlObjectTranslate translate) {
        if (object == null || StringUtils.isBlank(objectPath)) {
            return;
        }
        try {
            if (object instanceof List) {
                for (Object subObject : (List) object) {
                    //LOG.debug("This is a list...");
                    parseProperty(subObject, objectPath);
                }
            } else {
                int index = objectPath.indexOf(".");
                if (index > 0) {
                    String currObjectPath = objectPath.substring(0, index);
                    String subObjectPath = objectPath.substring(index + 1);
                    Object currObject = getSimpleProperty(object, currObjectPath);
                    parseProperty(currObject, subObjectPath);
                } else {
                    Object retObject = getSimpleProperty(object, objectPath);
                    if (retObject instanceof Integer) {
                        retObject = translate.translate(((Integer) retObject).intValue());
                        setSimpleProperty(object, objectPath, retObject);
                    }
                    LOG.debug("{} : {} : {}", object, objectPath, retObject);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null) {
            return null;
        }

        Object obj = beanClass.newInstance();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter != null) {
                setter.invoke(obj, map.get(property.getName()));
            }
        }

        return obj;
    }

    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            LOG.debug("{}", property.toString());
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            map.put(key, value);
        }

        return map;
    }


    public static Map<String, Object> objectToFlatMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }

            LOG.debug("{}", property.toString());
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            try {
                value = GhlObjectAdapter.getValue(value);
                map.put(key, value);
            } catch (ClassNotRegisterException e) {
                LOG.debug("------------------------");
                LOG.debug("", e);
                Map<String, Object> propertyMap = objectToFlatMap(value);
                propertyMap.forEach((mapKey, mapVal) -> map.put(key + "." + mapKey, mapVal));
            }
        }
        return map;
    }

}
