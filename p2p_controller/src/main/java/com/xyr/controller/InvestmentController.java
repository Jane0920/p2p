package com.xyr.controller;

import com.xyr.cache.BaseCacheService;
import com.xyr.domain.User;
import com.xyr.domain.UserAccount;
import com.xyr.service.UserAccountService;
import com.xyr.utils.ResponseCode;
import com.xyr.utils.ServerResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by xyr on 2017/9/8.
 * 投资控制类
 */
@Controller
@RequestMapping("/investment")
public class InvestmentController {

    @Autowired
    private BaseCacheService baseCacheService;
    @Autowired
    private UserAccountService userAccountService;

    /**
     * 判断投资金额是否满足条件（小于账户余额）
     *
     * @param token
     * @param account
     * @return
     */
    @RequestMapping("/checkAccount")
    @ResponseBody
    public ServerResponse checkAccount(@RequestHeader(value = "token") String token, int account) {
        if (StringUtils.isEmpty(token))
            return ServerResponse.createByError(ResponseCode.LOGIN_INVALID.getCode());

        Map<String, Object> userMap = baseCacheService.getHmap(token);
        if (userMap == null || userMap.size() == 0)
            return ServerResponse.createByError(ResponseCode.LOGIN_INVALID.getCode());

        UserAccount userAccount = userAccountService.findByUserId((int) userMap.get("id"));
        if (userAccount == null)
            return ServerResponse.createByError(ResponseCode.LOGIN_INVALID.getCode());

        if (userAccount.getBalance() >= account)
            return ServerResponse.createBySuccess();
        else
            return ServerResponse.createByError(ResponseCode.CAPITAL_TOO_LARGE.getCode());
    }

}
