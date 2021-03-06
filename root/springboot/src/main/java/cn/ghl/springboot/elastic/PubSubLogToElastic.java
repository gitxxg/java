package cn.ghl.springboot.elastic;

import static cn.ghl.springboot.utils.JsonMapUtils.getJsonMap;
import static cn.ghl.springboot.utils.JsonMapUtils.setJsonMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 7/20/2018
 */
public class PubSubLogToElastic {

    private static Logger LOG = LoggerFactory.getLogger(PubSubLogToElastic.class);

    //@Autowired
    private RestTemplate restTemplate = new RestTemplate();

    //@Value("${elasticsearch.url}")
    private String elasticsearchURL = "http://192.168.83.200:9200/ps-gateway_2018/pubsub";

    public static void main(String args[]) {
        PubSubLogToElastic kongLogToElastic = new PubSubLogToElastic();
        kongLogToElastic.run();
    }

    public void run() {
        String file = "elastic/kong-log/ps.json";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            Map map = objectMapper.readValue(new ClassPathResource(file).getFile(), Map.class);

            String msg;
            for (int i = 0; i < 200; i++) {
                msg = RandomData.getMethod() + "||" + RandomData.getUserId() + "||" + RandomData.getAppName() + "||" + RandomData.getApiName() + "||"
                    + RandomData.getTimestamp() + "||" + RandomData.getStatus();
                LOG.info(msg);
                setJsonMap(map, "userId", RandomData.getUserId());
                setJsonMap(map, "appName", RandomData.getAppName());
                setJsonMap(map, "apiName", RandomData.getApiName());
                //setJsonMap(map, "response.status", RandomData.getStatus());
                setJsonMap(map, "@timestamp", RandomData.getTimestamp());
                //setJsonMap(map, "latencies.request", RandomUtils.nextInt(10) + 10);

                msg = objectMapper.writeValueAsString(map);
                LOG.info("json {} : {}", i, map);
                postToElastic(msg);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void postToElastic(String jsonData) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonData, headers);

        Map map = restTemplate.postForObject(elasticsearchURL, entity, Map.class);
        LOG.info("{}", map.get("result"));

    }

}
