package cn.ghl.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@EnableAutoConfiguration
public class WebApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() {
        String res = testRestTemplate.getForObject("/web/stock/context/sz000651", String.class);
        //Assert.assertEquals(result.getCode(),0);
    }

    @Test
    public void elasticsearch() {
        String res = testRestTemplate.getForObject("/web/elasticsearch/context", String.class);
    }

    @Test
    public void elasticsearchRestful() {
        String res = testRestTemplate.getForObject("http://127.0.0.1:9200/xmp/news/_search", String.class);
        System.out.println(res);

        String reqJsonStr = "{\n"
            + "   \"query\":{\n"
            + "      \"range\":{\n"
            + "         \"clickCount\":{\n"
            + "            \"gte\":3\n"
            + "         }\n"
            + "      }\n"
            + "   }\n"
            + "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(reqJsonStr,headers);

        res = testRestTemplate.postForObject("http://127.0.0.1:9200/xmp/news/_search", entity, String.class);
        System.out.println(res);
    }

}
