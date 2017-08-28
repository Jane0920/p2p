package com.xyr.controller;

import com.xyr.domain.Admin;
import com.xyr.service.AccountService;
import com.xyr.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xyr on 2017/8/25.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/login")
    @ResponseBody
    public ServerResponse login(String username, String password) {
        Admin admin = accountService.login(username, password);
        if (admin != null)
            return ServerResponse.createBySuccess();
        else
            return ServerResponse.createByError();
    }

}
