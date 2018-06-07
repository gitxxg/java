package cn.ghl.tester.mock;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 5/21/2018
 */
public class MockHeader {

    private String header = "";

    public MockHeader function(String fun) {
        return new MockHeader();
    }

    public String method() {
        return this.header;
    }

    public String show() {
        return "this is show";
    }

    public List<String> list() {
        return new ArrayList();
    }
}
