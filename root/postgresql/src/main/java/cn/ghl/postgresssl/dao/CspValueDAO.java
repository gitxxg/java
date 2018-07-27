package cn.ghl.postgresssl.dao;

import cn.ghl.postgresssl.dao.entity.CspValue;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 6/28/2018
 */
@Repository
@Transactional
public class CspValueDAO extends HibernateGenericDAOImpl<CspValue, Integer> {

}
