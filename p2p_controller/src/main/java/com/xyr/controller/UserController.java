package com.xyr.controller;

import com.xyr.utils.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xyr on 2017/8/31.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 获取验证码，并且保存到redis中
     *
     * @return
     */
    @RequestMapping("/uuid")
    @ResponseBody
    public ServerResponse getUuid() {

    }

}
