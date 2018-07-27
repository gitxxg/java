package cn.ghl.postgresssl.controller;

import cn.ghl.postgresssl.dao.CspValueDAO;
import cn.ghl.postgresssl.dao.entity.CspValue;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 6/28/2018
 */
@Controller
public class MyController {

    @Autowired
    private CspValueDAO cspValueDAO;

    @RequestMapping(value = "/db", method = RequestMethod.GET)
    public ResponseEntity<String> fetchClientInfo(@RequestParam(required = false) String method) {
        List<CspValue> list = cspValueDAO.findAll();
        for (CspValue cspValue : list) {
            System.out.println(cspValue.toString());
        }
        return ResponseEntity.ok("123");
    }
}
