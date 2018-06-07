package cn.ghl.sbsc.server.controller;

import cn.ghl.sbsc.api.SayHello;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 3/28/2018
 */

@RestController
public class SayHelloWorld implements SayHello {

    public String say(String name) {
        return "hello world, " + name;
    }
}
