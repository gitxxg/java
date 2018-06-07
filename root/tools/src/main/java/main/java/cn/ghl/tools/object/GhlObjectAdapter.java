package main.java.cn.ghl.tools.object;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/19/2018
 */
public class GhlObjectAdapter {

    private static Logger LOG = LoggerFactory.getLogger(GhlObjectAdapter.class);

    private static Map<Class, GhlObjectValue> adapter = new HashMap<>();

    static {
        adapter.put(Number.class, object -> object);
        adapter.put(String.class, object -> object);
        adapter.put(List.class, object -> object);
        adapter.put(Enum.class, object -> object);
        adapter.put(Map.class, object -> object);
        adapter.put(Set.class, object -> object);
        adapter.put(Date.class, object -> ((Date) object).getTime());
    }

    public static void register(Class clazz, GhlObjectValue objectValue) {
        adapter.put(clazz, objectValue);
    }

    public static Object getValue(Object object) throws ClassNotRegisterException {
        if (object == null) {
            return null;
        }
        Class clazz = object.getClass();
        LOG.debug("{}", clazz.getName());
        for (Entry<Class, GhlObjectValue> entry : adapter.entrySet()) {
            if (entry.getKey().isAssignableFrom(clazz)) {
                return entry.getValue().getValue(object);
            }
        }
        throw new ClassNotRegisterException(clazz);
    }
}
