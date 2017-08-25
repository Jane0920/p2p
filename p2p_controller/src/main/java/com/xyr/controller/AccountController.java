package com.xyr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyr on 2017/8/25.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @RequestMapping("/login")
    @ResponseBody
    public Map login(String username, String password) {
        Map map = new HashMap();
        map.put("status", 1);
        return map;
    }

}
