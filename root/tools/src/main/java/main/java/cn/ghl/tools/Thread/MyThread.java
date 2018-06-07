package main.java.cn.ghl.tools.Thread;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/16/2018
 */
public class MyThread {

    private static Logger LOG = LoggerFactory
        .getLogger(MyThread.class);


    private static MyThread thread;

    private String name;

    public static void main(String args[]) {
        List<Thread> list = new ArrayList<>();
        int num = 50;
        for (int i = 0; i < num; i++) {
            Thread th = new TestThread();
            th.start();
            list.add(th);
        }
        try {
            for (Thread thread : list) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static MyThread getThread() {
        if (thread == null) {
            synchronized (MyThread.class) {
                if (thread == null) {
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    thread = new MyThread();
                    MyThread.thread.name = "name";
                }
            }
        }
        return thread;
    }

    public void setThread(MyThread thread) {
        this.thread = thread;
    }

    static class TestThread extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 50; i++) {
                MyThread thread = MyThread.getThread();
                LOG.debug("{}, {}", thread.name, thread);
                //thread.setThread(null);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
