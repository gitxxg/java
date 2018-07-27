package cn.ghl.web.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.StringWriter;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 6/12/2018
 */
public class JacksonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object) {
        StringWriter str = new StringWriter();
        try {
            objectMapper.writeValue(str, object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }
}
