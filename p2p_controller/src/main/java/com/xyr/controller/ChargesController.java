package com.xyr.controller;

import com.xyr.cache.BaseCacheService;
import com.xyr.domain.BankCardInfo;
import com.xyr.service.BankCardInfoService;
import com.xyr.service.ChargesService;
import com.xyr.service.UserAccountService;
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
 * Created by xyr on 2017/9/11.
 */
@Controller
@RequestMapping("/charges")
public class ChargesController {

    @Autowired
    private BaseCacheService baseCacheService;
    @Autowired
    private ChargesService chargesService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private BankCardInfoService bankCardInfoService;

    @RequestMapping("/charge")
    @ResponseBody
    public ServerResponse charge(@RequestHeader(value = "token") String token, Double chargeMoney) {
        if (StringUtils.isEmpty(token))
            return ServerResponse.createByError(ResponseCode.NULL_TOKEN.getCode());

        Map<String, Object> userMap = baseCacheService.getHmap(token);
        if (userMap == null || userMap.size() == 0)
            return ServerResponse.createByError(ResponseCode.LOGIN_INVALID.getCode());

        int userId = (int) userMap.get("id");
        BankCardInfo bankCardInfo = bankCardInfoService.findByUserId(userId);
        if (bankCardInfo == null)
            return ServerResponse.createByError();
        chargesService.reCharge(chargeMoney, bankCardInfo.getBankCardNum());

        return userAccountService.updateBalanceAndTotal(chargeMoney, userId);
    }

}
