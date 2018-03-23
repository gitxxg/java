package cn.ghl.myssm.dao;

import cn.ghl.myssm.entity.Appointment;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 3/20/2018
 */
public interface AppointmentDao {

    /**
     * 插入预约图书记录
     *
     * @return 插入的行数
     */
    int insertAppointment(@Param("bookId") long bookId, @Param("studentId") long studentId);

    /**
     * 通过主键查询预约图书记录，并且携带图书实体
     */
    Appointment queryByKeyWithBook(@Param("bookId") long bookId, @Param("studentId") long studentId);

}
