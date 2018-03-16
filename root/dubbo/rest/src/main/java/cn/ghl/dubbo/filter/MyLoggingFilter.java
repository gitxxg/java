package cn.ghl.dubbo.filter;

import com.alibaba.dubbo.rpc.protocol.rest.support.LoggingFilter;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import javax.ws.rs.core.MultivaluedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 3/12/2018
 */
public class MyLoggingFilter extends LoggingFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    protected void logHttpHeaders(MultivaluedMap<String, String> headers) {
        StringBuilder msg = new StringBuilder("The HTTP headers are: \n");
        System.out.println("-------------------logHttpHeaders---------------------------");
        Iterator i$ = headers.entrySet().iterator();

        while (i$.hasNext()) {
            Entry<String, List<String>> entry = (Entry) i$.next();
            msg.append((String) entry.getKey()).append(": ");

            for (int i = 0; i < ((List) entry.getValue()).size(); ++i) {
                msg.append((String) ((List) entry.getValue()).get(i));
                if (i < ((List) entry.getValue()).size() - 1) {
                    msg.append(", ");
                }
            }

            MDC.put("key", "event LoggingFilter -- " + System.currentTimeMillis());

            msg.append("\n");
        }

        logger.info(msg.toString());
    }
}
