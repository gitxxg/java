package cn.ghl.postgresssl;

import cn.ghl.postgresssl.dao.CspValueDAO;
import cn.ghl.postgresssl.dao.entity.CspValue;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class PostgresSslApplication {

    public static void main(String[] args) {
        PostgresSslApplication app = new PostgresSslApplication();
        app.testJdbc();
    }

    public void testJdbc() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-*.xml");
        System.out.println("---------------- JdbcTemplate ----------------");
        JdbcTemplate jdbcTemplate = (JdbcTemplate) ac.getBean("jdbcTemplate");
        System.out.println(jdbcTemplate.toString());
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("select * from csp_value");
        for (Map<String, Object> map : mapList) {
            System.out.println(map.toString());
        }


    }

    public void testHibernate() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-*.xml");
        System.out.println("---------------- Hibernate ----------------");
        CspValueDAO cspValueDAO = (CspValueDAO) ac.getBean("cspValueDAO");
        MyThread myThread = new MyThread(ac);
        myThread.start();

        System.out.println(cspValueDAO);
        List<CspValue> list = cspValueDAO.findAll();
        for (CspValue cspValue : list) {
            System.out.println(cspValue.toString());
        }
    }

    @Transactional
    public class MyThread extends Thread {

        private ApplicationContext ac;

        public MyThread(ApplicationContext ac) {
            this.ac = ac;
        }

        @Override
        @Transactional
        public void run() {
            CspValueDAO cspValueDAO = (CspValueDAO) ac.getBean("cspValueDAO");
            List<CspValue> list = cspValueDAO.findAll();
            for (CspValue cspValue : list) {
                System.out.println(cspValue.toString());
            }
        }
    }
}
