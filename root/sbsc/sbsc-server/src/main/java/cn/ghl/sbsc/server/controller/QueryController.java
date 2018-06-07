package cn.ghl.sbsc.server.controller;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 3/23/2018
 */
@RestController
public class QueryController {

    @Value("${server.port}")
    private int port;

    @RequestMapping("/query/{id}")
    public String query(@PathVariable String id) {
        return "{date: " + new Date() + " , id: " + id + ", port:" + port + "}";
    }
}
