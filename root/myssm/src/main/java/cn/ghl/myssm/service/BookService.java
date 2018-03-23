package cn.ghl.myssm.service;

import cn.ghl.myssm.dto.AppointExecution;
import cn.ghl.myssm.entity.Book;
import java.util.List;

/**
 * @Author: Hailong Gong
 * @Description: 业务接口：站在"使用者"角度设计接口 三个方面：方法定义粒度，参数，返回类型（return 类型/异常）
 * @Date: Created in 3/20/2018
 */
public interface BookService {

    /**
     * 查询一本图书
     */
    Book getById(long bookId);

    /**
     * 查询所有图书
     */
    List<Book> getList();

    /**
     * 预约图书
     */
    AppointExecution appoint(long bookId, long studentId);
}
