package com.ericsson.justlog.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 7/10/2018
 */
public class Log2stash {

    private static Logger LOG = LoggerFactory.getLogger(Log2stash.class);

    public static void write(String msg) {
        LOG.debug(msg);
    }
}
