package cn.ghl.web.entity;

import java.io.Serializable;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 6/11/2018
 */
public class Author implements Serializable {

    /**
     * 作者id
     */
    private Long id;

    /**
     * 作者姓名
     */
    private String name;

    /**
     * 作者简介
     */
    private String remark;

    public Author() {

    }

    public Author(Long id, String name, String remark) {
        this.id = id;
        this.name = name;
        this.remark = remark;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Author{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", remark='" + remark + '\'' +
            '}';
    }
}
