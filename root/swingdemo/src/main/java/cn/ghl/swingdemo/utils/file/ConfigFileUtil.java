package cn.ghl.swingdemo.utils.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import org.apache.commons.io.FileUtils;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 12/29/2017
 */
public class ConfigFileUtil {

    public static final Gson gson = new GsonBuilder().create();

    public static void writeObject(File file, Object object) {
        try {
            String str = gson.toJson(object);
            FileUtils.write(file, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public static Object readObject(File file, Class clazz) {
        try {
            String str = FileUtils.readFileToString(file);
            Object object = gson.fromJson(str, clazz);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
