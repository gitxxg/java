package cn.ghl.sbsc.feign;

import cn.ghl.sbsc.feign.service.MyHello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 3/28/2018
 */

@RestController
public class Hello {

    @Autowired
    private MyHello sayHello;

    @RequestMapping(value = "/hi")
    public String show(@RequestParam String name) {
        String tmp = sayHello.show(name);
        System.out.println(tmp);
        return tmp;
    }
}
