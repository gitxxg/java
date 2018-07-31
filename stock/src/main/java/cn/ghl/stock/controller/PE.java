package cn.ghl.stock.controller;

import cn.ghl.stock.utils.JsonMapUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 7/27/2018
 */
@CrossOrigin
@RestController
@Api(value = "PubSub Api report Version 1.0")
@RequestMapping(path = "/pe")
public class PE {

    private static final Logger LOG = LoggerFactory.getLogger(PE.class);

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "Get pub-sub api analytics of application")
    @ApiResponses({
        @ApiResponse(code = 200, message = "pub-sub api analytics"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(value = "/history", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> queryPEAnalytics(

        @ApiParam(name = "stock", value = "The id of the stock")
        @RequestParam(required = true) String stockId,

        @ApiParam(name = "lastdate", value = "The lastdate of query")
        @RequestParam(required = true) String lastdate) {

        String url = "https://www.touzid.com/index.php?/s_company/ajax/fw/";

        /*
        "{"
        + " \"symbol\": \"sz000418\", "
        + " \"lastdate\": \"2018-07-30\", "
        + " \"tableFields\": [\"d_pe_ttm\", "
        + " \"market_value\", "
        + " \"price_n\", "
        + " \"price_adj\"] "
        + "}";
        */

        String reqJson = "{"
            + " \"symbol\": \"%s\", "
            + " \"lastdate\": \"%s\", "
            + " \"tableFields\": [\"d_pe_ttm\", "
            + " \"market_value\", "
            + " \"price_n\", "
            + " \"price_adj\"] "
            + "}";

        reqJson = String.format(reqJson, stockId, lastdate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(reqJson, headers);

        LOG.info("post: {} \n {}", url, reqJson);

        String res = restTemplate.postForObject(url, entity, String.class);
        LOG.info("res {}", res);
        //Pattern pattern = Pattern.compile("[0-9]{4}-[0][3|6|9]-[0-9]{2}");
        //Matcher matcher;
        try {
            Map map = JsonMapUtils.objectMapper.readValue(res, Map.class);
            //LOG.info("errno {}", map.get("errno"));
            List<Map<String, Object>> itemList = (List<Map<String, Object>>) map.get("rsm");
            // 动态PE
            float dpe = 0;
            // 前复权
            float price = 0;
            // 计数
            int num = 0;
            //
            String month = "";
            String year = "";
            for (Map<String, Object> item : itemList) {
                String date = (String) item.get("date");
                //LOG.info("date {}", date);
                String[] ymd = date.split("-");
                // 按季度输出
                if ("03|06|09|12".contains(ymd[1])) {
                    //LOG.info(">>> date {}", date);
                    year = ymd[0];
                    month = ymd[1];
                    dpe += NumberUtils.createFloat((String) JsonMapUtils.getJsonMap(item, "items.d_pe_ttm"));
                    price += NumberUtils.createFloat((String) JsonMapUtils.getJsonMap(item, "items.price_adj"));
                    num++;
                } else if (StringUtils.isNotBlank(month)) {
                    //LOG.info("{}-{} dpe {}", ymd[0], month, dpe / num);
                    //LOG.info("{}-{} price {}", ymd[0], month, price / num);
                    //LOG.info("{}-{} {} {}", year, month, dpe / num, price / num);
                    // 月份, 月平均PE, 月平均价格
                    // 2018-06, 28.265661, 69.868
                    System.out.println(year + "-" + month + ", " + dpe / num + ", " + price / num);
                    year = "";
                    month = "";
                    dpe = 0;
                    price = 0;
                    num = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
