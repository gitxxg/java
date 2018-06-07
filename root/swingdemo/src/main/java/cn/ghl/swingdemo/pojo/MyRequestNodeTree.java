package cn.ghl.swingdemo.pojo;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/1/2018
 */
public class MyRequestNodeTree extends DefaultMutableTreeNode {

    public MyRequestNodeTree(MyRequestNode node) {
        super(node);
    }

    public MyRequestNode getNode() {
        if (userObject == null) {
            return null;
        } else {
            return (MyRequestNode) userObject;
        }
    }

    public void setNode(MyRequestNode node) {
        this.userObject = node;
    }

    @Override
    public String toString() {
        if (userObject == null) {
            return "***";
        } else {
            return ((MyRequestNode) userObject).getName();
        }
    }
}
