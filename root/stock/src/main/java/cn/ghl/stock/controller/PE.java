package cn.ghl.stock.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
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
}
