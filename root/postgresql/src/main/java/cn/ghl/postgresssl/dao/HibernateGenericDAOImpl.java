package cn.ghl.postgresssl.dao;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import java.io.Serializable;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 6/28/2018
 */
public class HibernateGenericDAOImpl<T, ID extends Serializable> extends GenericDAOImpl<T, ID> {

    @Autowired
    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        System.out.println("----------------- setSessionFactory");
        super.setSessionFactory(sessionFactory);
    }

}
