package cn.ghl.tester.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 4/11/2018
 */
public class LogTest {

    private static Logger logger = LoggerFactory.getLogger(LogTest.class);

    public static void main(String args[]) {
        logger.debug("------------ {}", "debug");
        logger.info("------------ {}", "info");
        logger.error("------------ {}", "error");
        logger.error("------------", new Exception("Exception"));
        logger.trace("------------ {}", "trace");
    }

}
