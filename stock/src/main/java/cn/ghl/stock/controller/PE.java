package cn.ghl.stock.controller;

import cn.ghl.stock.utils.JsonMapUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        @ApiParam(name = "date", value = "The date of query: 2017-09-29 or 2017-09 or 2017-09-29")
        @RequestParam(required = true) String date) {

        //String req = "http://www.szse.cn/api/report/ShowReport/data?SHOWTYPE=JSON&CATALOGID=1815_stock&TABKEY=tab1&txtDMorJC=002508&txtBeginDate=2017-09-29&txtEndDate=2017-09-29&radioClass=00%2C20%2C30&txtSite=all&random=0.9481951323835971";
        String req = "http://www.szse.cn/api/report/ShowReport/data?SHOWTYPE=JSON&CATALOGID=1815_stock&TABKEY=tab1&txtDMorJC="
            + stockId
            + "&txtBeginDate="
            + date
            + "&txtEndDate="
            + date
            + "&radioClass=00%2C20%2C30&txtSite=all&random=0.9481951323835971";
        return restTemplate.getForEntity(req, String.class);
    }

    @ApiOperation(value = "Get pub-sub api analytics of application")
    @ApiResponses({
        @ApiResponse(code = 200, message = "pub-sub api analytics"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(value = "/history/year", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> queryPEsOfYear(

        @ApiParam(name = "stockId", value = "The id of the stock")
        @RequestParam(required = true) String stockId,

        @ApiParam(name = "year", value = "The date of query: 2017-09-29 or 2017-09 or 2017-09-29")
        @RequestParam(required = true) String year,

        @ApiParam(name = "interval", value = "The interval of query month: 1, 3")
        @RequestParam(required = false) Integer interval) throws IOException {

        if (interval == null) {
            interval = 1;
        } else if ("1,3".indexOf("" + interval) < 0) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        int y = NumberUtils.createInteger(year);
        for (int m = 1; m <= 12; m++) {
            if ((m % interval) != 0) {
                continue;
            }
            //设置年份
            cal.set(y, m - 1, 1);
            //获取某月最大天数
            int lastDay = cal.getActualMaximum(Calendar.DATE);
            //月末
            String lastDayOfMonth = String.format("%04d-%02d-%02d", y, m, lastDay);

            // 报告期时间不能大于当前日期
            Calendar now = Calendar.getInstance();
            String currentDate = format.format(now.getTime());
            if (lastDayOfMonth.compareToIgnoreCase(currentDate) > 0) {
                LOG.info("{} is bigger than {}", lastDayOfMonth, currentDate);
                break;
            }

            // 查询月末数据
            Map map = dataOfEndMonth(stockId, y, m, lastDay);
            //LOG.info("map {}", map);
            //LOG.info("lastDayOfMonth {}, peMaxDayOfMonth {}, ss {}, syl1 {}", lastDayOfMonth, peMaxDayOfMonth, map.get("ss"), map.get("syl1"));
            LOG.info("{},{},{}", lastDayOfMonth, map.get("ss"), map.get("syl1"));
        }

        return null;
    }

    private String queryPE(String stockId, String date) {
        String req = "http://www.szse.cn/api/report/ShowReport/data?SHOWTYPE=JSON&CATALOGID=1815_stock&TABKEY=tab1&txtDMorJC="
            + stockId
            + "&txtBeginDate="
            + date
            + "&txtEndDate="
            + date
            + "&radioClass=00%2C20%2C30&txtSite=all&random=0.9481951323835971";
        ResponseEntity<String> res = restTemplate.getForEntity(req, String.class);
        return res.getBody();
    }

    private Map dataOfEndMonth(String stockId, int year, int month, int lastDay) {
        String peMaxDayOfMonth;
        String res;
        Map map = null;
        List list = null;
        do {
            // 月末没有数据，可能是遇到节假日，需要往前查询
            peMaxDayOfMonth = String.format("%04d-%02d-%02d", year, month, lastDay);
            //LOG.info("maxDateOfMonth {}", maxDateOfMonth);
            res = queryPE(stockId, peMaxDayOfMonth);
            res = res.substring(1, res.length() - 1);
            try {
                map = JsonMapUtils.objectMapper.readValue(res, Map.class);
                // "data": [{...}]
                list = (List) map.get("data");
                if (list != null && !list.isEmpty()) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } while (--lastDay > 0);

        return (Map) list.get(0);
    }
}
