package cn.ghl.sbsc.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 3/28/2018
 */
public interface ShowTime {

    @RequestMapping(value = "/say", method = RequestMethod.GET)
    String say(@RequestParam(value = "name") String name);

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    String show(@RequestParam(value = "name") String name);

}
