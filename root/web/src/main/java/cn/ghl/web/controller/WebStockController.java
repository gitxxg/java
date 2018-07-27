package cn.ghl.web.controller;

import cn.ghl.web.entity.MainTarget;
import cn.ghl.web.service.MainTargetService;
import cn.ghl.web.tools.JacksonUtil;
import cn.ghl.web.tools.WebContextClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 6/7/2018
 */
@RestController
public class WebStockController {

    private static final Logger logger = LoggerFactory.getLogger(WebStockController.class);

    private static final String FORMAT = "%s|";

    @Autowired
    private WebContextClient webContextClient;

    @Autowired
    private MainTargetService mainTargetService;

    @RequestMapping(value = "/web/stock/context/{stockName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getStockInfo(@PathVariable("stockName") String stockName) {

        // 主要财务指标按报告期
        // Major financial indicators by reporting period
        // "http://emweb.securities.eastmoney.com/NewFinanceAnalysis/MainTargetAjax?ctype=4&type=0&code=sz000651"
        String url = "http://emweb.securities.eastmoney.com/NewFinanceAnalysis/MainTargetAjax?ctype=4&type=0&code=";
        url += stockName;

        // get stock finance analysis from web
        String html = webContextClient.getWebContext(url);
        logger.info("json：{}", html);

        // pares json to object
        List<Map<String, String>> stockInfo = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            stockInfo = objectMapper.readValue(html, stockInfo.getClass());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("", e);
        }
        logger.info("list：{}", stockInfo);

        // format data
        // headline|item|item|item|item|item|item|item|item|
        List<String[]> targetList = getMainTargetList();
        StringBuffer ret = new StringBuffer();
        StringBuffer date = new StringBuffer();
        // header
        for (String[] target : targetList) {
            date.append(String.format(FORMAT, target[1]));
        }
        date.append("\n");
        for (Map<String, String> stockMap : stockInfo) {
            // items A|B|C|D|E|...

            for (String[] target : targetList) {
                date.append(String.format(FORMAT, stockMap.get(target[0])));
            }
            date.append("\n");
            /*
            try {
                saveStock(stockName, stockMap);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            */
        }
        logger.info("\n{}", date.toString());
        return ret.toString();
    }

    private void saveStock(String stockName, Map<String, String> stockMap) throws InvocationTargetException, IllegalAccessException {
        MainTarget mainTarget = new MainTarget();
        BeanUtils.setProperty(mainTarget, "name", stockName);
        for (String key : stockMap.keySet()) {
            BeanUtils.setProperty(mainTarget, key, stockMap.get(key));
        }
        logger.info("mainTarget json\n{}", JacksonUtil.toJson(mainTarget));
        mainTargetService.save(mainTarget);

    }

    private List<String[]> getMainTargetList() {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"date", "每股指标"});
        list.add(new String[]{"jbmgsy", "基本每股收益(元)"});
        list.add(new String[]{"kfmgsy", "扣非每股收益(元)"});
        list.add(new String[]{"xsmgsy", "稀释每股收益(元)"});
        list.add(new String[]{"mgjzc", "每股净资产(元)"});
        list.add(new String[]{"mggjj", "每股公积金(元)"});
        list.add(new String[]{"mgwfply", "每股未分配利润(元)"});
        list.add(new String[]{"mgjyxjl", "每股经营现金流(元)"});
        list.add(new String[]{"date", "成长能力指标"});
        list.add(new String[]{"yyzsr", "营业总收入(元)"});
        list.add(new String[]{"mlr", "毛利润(元)"});
        list.add(new String[]{"gsjlr", "归属净利润(元)"});
        list.add(new String[]{"kfjlr", "扣非净利润(元)"});
        list.add(new String[]{"yyzsrtbzz", "营业总收入同比增长(%)"});
        list.add(new String[]{"gsjlrtbzz", "归属净利润同比增长(%)"});
        list.add(new String[]{"kfjlrtbzz", "扣非净利润同比增长(%)"});
        list.add(new String[]{"yyzsrgdhbzz", "营业总收入滚动环比增长(%)"});
        list.add(new String[]{"gsjlrgdhbzz", "归属净利润滚动环比增长(%)"});
        list.add(new String[]{"kfjlrgdhbzz", "扣非净利润滚动环比增长(%)"});
        list.add(new String[]{"date", "盈利能力指标"});
        list.add(new String[]{"jqjzcsyl", "加权净资产收益率(%)"});
        list.add(new String[]{"tbjzcsyl", "摊薄净资产收益率(%)"});
        list.add(new String[]{"tbzzcsyl", "摊薄总资产收益率(%)"});
        list.add(new String[]{"mll", "毛利率(%)"});
        list.add(new String[]{"jll", "净利率(%)"});
        list.add(new String[]{"sjsl", "实际税率(%)"});
        list.add(new String[]{"date", "盈利质量指标"});
        list.add(new String[]{"yskyysr", "预收款/营业收入"});
        list.add(new String[]{"xsxjlyysr", "销售现金流/营业收入"});
        list.add(new String[]{"jyxjlyysr", "经营现金流/营业收入"});
        list.add(new String[]{"date", "运营能力指标"});
        list.add(new String[]{"zzczzy", "总资产周转率(次)"});
        list.add(new String[]{"yszkzzts", "应收账款周转天数(天)"});
        list.add(new String[]{"chzzts", "存货周转天数(天)"});
        list.add(new String[]{"date", "财务风险指标"});
        list.add(new String[]{"zcfzl", "资产负债率(%)"});
        list.add(new String[]{"ldzczfz", "流动负债/总负债(%)"});
        list.add(new String[]{"ldbl", "流动比率"});
        list.add(new String[]{"sdbl", "速动比率"});
        return list;
    }

}
