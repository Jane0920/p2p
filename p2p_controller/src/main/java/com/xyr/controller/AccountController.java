package com.xyr.controller;

import com.xyr.cache.BaseCacheService;
import com.xyr.domain.Admin;
import com.xyr.domain.UserAccount;
import com.xyr.service.AdminService;
import com.xyr.service.UserAccountService;
import com.xyr.utils.ResponseCode;
import com.xyr.utils.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by xyr on 2017/8/25.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    private final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AdminService adminService;
    @Autowired
    private BaseCacheService baseCacheService;
    @Autowired
    private UserAccountService userAccountService;

    /**
     * 后台登录
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public ServerResponse login(String username, String password) {
        logger.info("login starting----------------------");
        Admin admin = adminService.login(username, password);
        if (admin != null)
            return ServerResponse.createBySuccess();
        else
            return ServerResponse.createByError();
    }

    /**
     * 获取用户帐户主页面信息
     *
     * @param token
     * @return
     */
    @RequestMapping("/accountHomepage")
    @ResponseBody
    public ServerResponse accountHomepage(@RequestHeader(value = "token") String token) {

        Map<String, Object> userMap = baseCacheService.getHmap(token);
        if (userMap == null || userMap.size() == 0)
            return ServerResponse.createByError(ResponseCode.LOGIN_INVALID.getCode());

        int userId = (int) userMap.get("id");
        UserAccount userAccount = userAccountService.findByUserId(userId);
        return ServerResponse.createBySuccess(userAccount);

    }

}
