package cn.ghl.stock.controller;

import cn.ghl.stock.utils.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 7/30/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PETest {

    private static final Logger LOG = LoggerFactory.getLogger(PETest.class);

    public static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void queryPEsOfYears() throws Exception {

        String stockId = "002508";
        String[] years = {"2016", "2017", "2018"};
        for (String year : years) {
            String url = "/pe/history/year"
                + "?stockId="
                + stockId  // stock
                + "&year="
                + year  // year
                + "&interval=1";
            Object obj = testRestTemplate.getForObject(url, Object.class);
        }
    }

    @Test
    public void queryPEsOfYear() throws Exception {

        String url = "/pe/history/year"
            + "?stockId="
            + "002508"  // stock
            + "&year="
            + "2018"  // year
            + "&interval=3";
        Object obj = testRestTemplate.getForObject(url, Object.class);
    }


    @Test
    public void queryPEAnalytics() throws Exception {
        String url;
        String jsn;
        Object obj;
        List<String> dtList = DateUtils.getMaxDayOfWeeks("2018");
        for (String date : dtList) {
            url = "/pe/history"
                + "?stockId=002508&date="
                + date;
            obj = testRestTemplate.getForObject(url, Object.class);
            jsn = objectMapper.writeValueAsString(obj);
            LOG.info("res {}", jsn);
            jsn = jsn.substring(1, jsn.length() - 1);
            LOG.info("res {}", jsn);
        }
    }

}