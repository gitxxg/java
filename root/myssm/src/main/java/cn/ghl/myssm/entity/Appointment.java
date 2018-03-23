package cn.ghl.myssm.entity;

import java.util.Date;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 3/20/2018
 */
public class Appointment {

    private long bookId;// 图书ID

    private long studentId;// 学号

    private Date appointTime;// 预约时间

    // 多对一的复合属性
    private Book book;// 图书实体

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public Date getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(Date appointTime) {
        this.appointTime = appointTime;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Appointment{" +
            "bookId=" + bookId +
            ", studentId=" + studentId +
            ", appointTime=" + appointTime +
            ", book=" + book +
            '}';
    }
}
