package cn.ghl.swingdemo.utils.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 12/28/2017
 */
public class FileStreamUtil {

    public static void writeObject(File file, Object object) {
        try {
            //序列化文件输出流
            OutputStream outputStream = new FileOutputStream(file);
            //构建缓冲流
            BufferedOutputStream buf = new BufferedOutputStream(outputStream);
            //构建字符输出的对象流
            ObjectOutputStream obj = new ObjectOutputStream(buf);
            //写入对象
            obj.writeObject(object);
            //关闭对象流
            obj.close();
            //关闭缓冲流
            buf.close();
            //关闭文件输出流
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public static Object readObject(File file) {
        try {
            //序列化文件输入流
            InputStream inputStream = new FileInputStream(file);
            //构建缓冲流
            BufferedInputStream buf = new BufferedInputStream(inputStream);
            //构建字符输入的对象流
            ObjectInputStream obj = new ObjectInputStream(buf);

            Object object = obj.readObject();

            //关闭对象流
            obj.close();
            //关闭缓冲流
            buf.close();
            //关闭文件输入流
            inputStream.close();

            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
