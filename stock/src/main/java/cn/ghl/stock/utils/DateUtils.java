package cn.ghl.stock.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 7/30/2018
 */
public class DateUtils {

    private static final Logger LOG = LoggerFactory.getLogger(DateUtils.class);

    public static List<String> getMaxDayOfWeeks(String year) throws ParseException {
        List<String> list = new ArrayList<>();
        String dtf = "%04d-%02d-%02d";
        Calendar cal = new GregorianCalendar();
        int y = NumberUtils.createInteger(year);
        for (int m = 1; m <= 12; m++) {

            //设置年份
            cal.set(y, m - 1, 1);
            //LOG.info("{}", cal.getTime());
            //获取某月最小天数
            int firstDay = cal.getActualMinimum(Calendar.DATE);
            //获取某月最大天数
            int lastDay = cal.getActualMaximum(Calendar.DATE);
            //LOG.info("firstDay {}, lastDay {}", firstDay, lastDay);

            String maxDateOfWeek = String.format(dtf, y, m, lastDay);
            LOG.info("maxDateOfWeek {}", maxDateOfWeek);
            list.add(maxDateOfWeek);

        }
        return list;
    }
}
