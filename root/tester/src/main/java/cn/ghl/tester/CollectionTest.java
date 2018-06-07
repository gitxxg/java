package cn.ghl.tester;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/30/2018
 */
public class CollectionTest {

    private static Random random = new Random();

    public static void main(String[] args) {
        ArrayList<String> arraylist = new ArrayList();
        LinkedList<String> linkedlist = new LinkedList();
        HashMap<String, Object> map = new HashMap();
        HashSet<String> set = new HashSet();
        for (int i = 0; i < 50000; i++) {
            String r = randomString();
            arraylist.add(r);
            linkedlist.add(r);
            map.put(r, r);
            set.add(r);
        }

        System.out.println("arraylist size " + arraylist.size());
        System.out.println("linkedlist size " + linkedlist.size());
        System.out.println("map size " + map.size());
        System.out.println("set size " + set.size());

        System.out.println("------");
        long begin = System.currentTimeMillis();
        for (int j = 0; j < arraylist.size(); j++) {
            arraylist.contains(arraylist.get(j));
        }
        System.out.println("arraylist time " + (System.currentTimeMillis() - begin));

        begin = System.currentTimeMillis();
        for (int j = 0; j < arraylist.size(); j++) {
            linkedlist.contains(arraylist.get(j));
        }
        System.out.println("linkedlist time " + (System.currentTimeMillis() - begin));

        begin = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < arraylist.size(); j++) {
                map.containsKey(arraylist.get(j));
            }
        }
        System.out.println("map time " + (System.currentTimeMillis() - begin));

        begin = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < arraylist.size(); j++) {
                set.contains(arraylist.get(j));
            }
        }
        System.out.println("set time " + (System.currentTimeMillis() - begin));

    }

    protected static String randomString() {
        return Long.toString(random.nextLong(), 36);
    }
}
