package cn.ghl.tester.mock;

import java.util.List;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 5/21/2018
 */
public class MockParent {

    private MockHeader mockHeader;

    public String getHeader() {
        System.out.println("mockHeader = " + mockHeader);
        return mockHeader.function("q").method();
    }

    public String getShow() {
        System.out.println("mockHeader = " + mockHeader);
        return mockHeader.show();
    }

    public List<String> getList() {
        System.out.println("mockHeader = " + mockHeader);
        return mockHeader.list();
    }
}
