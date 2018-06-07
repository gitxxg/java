package main.java.cn.ghl.tools.json;

import com.google.gson.Gson;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import main.java.cn.ghl.tools.object.GhlObjectAdapter;
import main.java.cn.ghl.tools.object.GhlObjectUtil;
import main.java.cn.ghl.tools.object.GhlObjectValue;
import org.junit.Test;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/19/2018
 */
public class JsonTest {

    private Gson gson = new Gson();

    @Test
    public void testJson() {

        GHL_3 ghl_3 = new GHL_3();
        ghl_3.setAge(3);
        ghl_3.setName("ghl_3");

        GHL_2 ghl_2 = new GHL_2();
        ghl_2.setGhl_3(ghl_3);
        ghl_2.setAge(2);
        ghl_2.setName("ghl_2");

        GHL_1 ghl_1 = new GHL_1();
        ghl_1.setGhl_2(ghl_2);
        ghl_1.setAge(1);
        ghl_1.setName("ghl_1");

        String json = gson.toJson(ghl_1);
        json = PrettyJsonUtil.format(json);
        System.out.println(json);

        Map<String, Object> map = gson.fromJson(json, Map.class);
        System.out.println(PrettyJsonUtil.format(map.toString()));

        try {
            GhlObjectValue objectValue = (str) -> str + "----------";
            GhlObjectAdapter.register(String.class, objectValue);
            Map<String, Object> objectMap = GhlObjectUtil.objectToFlatMap(ghl_1);

            Map<String, Object> sortedMap = new TreeMap<>();
            objectMap.entrySet()
                .stream()
                //.sorted(Map.Entry.<String, Object>comparingByKey().reversed())
                .sorted(Comparator.comparing(Entry::getKey))
                .forEachOrdered(e -> sortedMap.put(e.getKey(), e.getValue()));

            sortedMap.forEach((key, val) -> System.out.println("key: " + key + "  val: " + val));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class GHL implements Serializable {

        private Integer age;

        private String name;

        private Date time = new Date();

        private List<String> list = Arrays.asList(new String[]{"a", "b", "c"});


        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }

    public class GHL_1 extends GHL {

        private GHL_2 ghl_2;

        public GHL_2 getGhl_2() {
            return ghl_2;
        }

        public void setGhl_2(GHL_2 ghl_2) {
            this.ghl_2 = ghl_2;
        }
    }

    public class GHL_2 extends GHL {

        private GHL_3 ghl_3;

        public GHL_3 getGhl_3() {
            return ghl_3;
        }

        public void setGhl_3(GHL_3 ghl_3) {
            this.ghl_3 = ghl_3;
        }
    }

    public class GHL_3 extends GHL {

    }
}
