package cn.ghl.myssm.service.impl;

import cn.ghl.myssm.BaseTest;
import cn.ghl.myssm.dto.AppointExecution;
import cn.ghl.myssm.service.BookService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 3/20/2018
 */
public class BookServiceImplTest extends BaseTest {

    @Autowired
    private BookService bookService;

    @Test
    public void testAppoint() throws Exception {
        long bookId = 1001;
        long studentId = 12345678910L;
        AppointExecution execution = bookService.appoint(bookId, studentId);
        System.out.println(execution);
    }
}
