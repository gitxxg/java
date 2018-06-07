package cn.ghl.sbsc.server.controller;

import cn.ghl.sbsc.api.ShowTime;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 3/28/2018
 */

@RestController
public class ShowNowTime implements ShowTime {

    @Value("${server.port}")
    private int port;

    public String say(String name) {
        return "ShowNowTime: say " + name + ",port: " + port;
    }

    public String show(String name) {
        return "ShowNowTime: show " + name + ", " + new Date() + ",port: " + port;
    }
}
