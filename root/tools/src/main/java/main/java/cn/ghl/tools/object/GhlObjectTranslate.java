package main.java.cn.ghl.tools.object;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/10/2018
 */
public class GhlObjectTranslate {

    private Integer factor;

    public GhlObjectTranslate(Integer factor) {
        this.factor = factor;
    }

    Integer translate(Integer value) {
        return value * factor;
    }
}
