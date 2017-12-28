package cn.ghl.tools.json;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class MyObjectUtil {

    private static Logger LOG = LoggerFactory
        .getLogger(MyObjectUtil.class);

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
     * @param objectPath
     * @return
     */
    public static Object getSimpleProperty(Object object, String objectPath) {
        if (object == null || StringUtils.isBlank(objectPath)) {
            return null;
        }
        try {
            Class clazz = object.getClass();
            String getProperty = "get" + StringUtils.capitalize(objectPath);
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
     * @param objectPath
     * @param value
     * @return
     */
    public static Object setSimpleProperty(Object object, String objectPath, Object value) {
        if (object == null || StringUtils.isBlank(objectPath)) {
            return null;
        }
        try {
            Class clazz = object.getClass();
            String setProperty = "set" + StringUtils.capitalize(objectPath);
            Class propertyType = PropertyUtils.getPropertyType(object, objectPath);
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

}
