package com.xyr.controller;

import com.xyr.cache.BaseCacheService;
import com.xyr.service.UserService;
import com.xyr.utils.ResponseCode;
import com.xyr.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by xyr on 2017/9/4.
 */
@Controller
@RequestMapping("/verification")
public class VerificationController {

    @Autowired
    private BaseCacheService baseCacheService;
    @Autowired
    private UserService userService;

    /**
     * 实名认证
     *
     * @param token
     * @param realName 真实姓名
     * @param identity 身份证
     * @return
     */
    @RequestMapping("/verifiRealName")
    @ResponseBody
    public ServerResponse verifiRealName(@RequestHeader(value = "token") String token, String realName, String identity) {

        if (StringUtils.isEmpty(token))
            return ServerResponse.createByError(ResponseCode.NULL_TOKEN.getCode());

        Map<String, Object> userMap = baseCacheService.getHmap(token);
        if (userMap == null || userMap.size() == 0)
            return ServerResponse.createByError(ResponseCode.LOGIN_INVALID.getCode());

        return userService.updateRealName(realName, identity, (int) userMap.get("id"));

    }

}
