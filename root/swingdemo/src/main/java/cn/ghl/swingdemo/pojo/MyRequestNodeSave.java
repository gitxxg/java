package cn.ghl.swingdemo.pojo;

import java.util.List;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/1/2018
 */
public class MyRequestNodeSave {

    private MyRequestNode node;

    private List<MyRequestNodeSave> childNodes;

    public MyRequestNode getNode() {
        return node;
    }

    public void setNode(MyRequestNode node) {
        this.node = node;
    }

    public List<MyRequestNodeSave> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<MyRequestNodeSave> childNodes) {
        this.childNodes = childNodes;
    }
}
