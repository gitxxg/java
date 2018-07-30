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
    public void queryPEAnalytics() throws Exception {
        String url;
        url = "/pe/history"
            + "?stockId=002508&date="
            + "2017-10-02";
        Object obj = testRestTemplate.getForObject(url, Object.class);
        LOG.info("res {}", objectMapper.writeValueAsString(obj));

        List<String> dtList = DateUtils.getMaxDayOfWeeks("2016");
        for (String date : dtList) {
            url = "/pe/history"
                + "?stockId=002508&date="
                + date;
            obj = testRestTemplate.getForObject(url, Object.class);
            LOG.info("res {}", objectMapper.writeValueAsString(obj));
        }
    }

}