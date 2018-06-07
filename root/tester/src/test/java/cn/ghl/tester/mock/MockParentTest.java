package cn.ghl.tester.mock;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 5/21/2018
 */
@RunWith(MockitoJUnitRunner.class)
public class MockParentTest {

    @InjectMocks
    private MockParent mockParent;

    //@Mock
    private MockHeader mockHeader = Mockito.mock(MockHeader.class, RETURNS_DEEP_STUBS);

    //@Mock
    //private MockHeader mockHeader;

    @Before
    public void init() {
        Mockito.when(mockHeader.function(Mockito.anyString()).method()).thenReturn("hello mock");
        Mockito.when(mockHeader.show()).thenReturn("hello show");
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("list");
        Mockito.when(mockHeader.list()).thenReturn(list);
    }

    @Test
    public void getHeaderTest() {
        System.out.println("--------------");
        String ret = mockParent.getHeader();
        System.out.print("ret = " + ret);
    }

    @Test
    public void getShowTest() {
        System.out.println("--------------");
        String ret = mockParent.getShow();
        System.out.print("ret = " + ret);
    }

    @Test
    public void getListTest() {
        System.out.println("--------------");
        List ret = mockParent.getList();
        System.out.print("ret = " + ret);
    }

}
