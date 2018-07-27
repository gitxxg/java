package cn.ghl.web.entity;

import java.io.Serializable;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 6/11/2018
 */
public class Tutorial implements Serializable {

    private Long id;

    private String name;//教程名称

    public Tutorial() {

    }

    public Tutorial(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tutorial{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
