package cn.ghl.dubbo.pojo;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 3/12/2018
 */
public class DubboMessage {

    private String name;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "DubboMessage{" +
            "name='" + name + '\'' +
            ", age=" + age +
            '}';
    }
}
