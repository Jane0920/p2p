package com.xyr.controller;

import com.xyr.cache.BaseCacheService;
import com.xyr.domain.Bank;
import com.xyr.domain.BankCardInfo;
import com.xyr.domain.City;
import com.xyr.domain.User;
import com.xyr.service.BankCardInfoService;
import com.xyr.service.BankService;
import com.xyr.service.CityService;
import com.xyr.service.UserService;
import com.xyr.utils.ResponseCode;
import com.xyr.utils.ServerResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by xyr on 2017/9/8.
 */
@Controller
@RequestMapping("/bankCardInfo")
public class BankCardInfoController {

    @Autowired
    private BaseCacheService baseCacheService;
    @Autowired
    private BankCardInfoService bankCardInfoService;
    @Autowired
    private BankService bankService;
    @Autowired
    private CityService cityService;
    @Autowired
    private UserService userService;

    /**
     * 根据用户名查询用户的银行卡
     *
     * @param token
     * @param username
     * @return
     */
    @RequestMapping("/findBankInfoByUsername")
    @ResponseBody
    public ServerResponse findBankInfoByUsername(@RequestHeader(value = "token") String token, String username) {
        if (StringUtils.isEmpty(token))
            return ServerResponse.createByError(ResponseCode.NULL_TOKEN.getCode());

        Map<String, Object> userMap = baseCacheService.getHmap(token);
        if (username == null || userMap == null || userMap.size() == 0)
            return ServerResponse.createByError(ResponseCode.LOGIN_INVALID.getCode());

        String _username = (String) userMap.get("username");
        if (username.startsWith(_username)) {
            int userId = (int) userMap.get("id");
            BankCardInfo bankCardInfo = bankCardInfoService.findByUserId(userId);
            if (bankCardInfo != null)
                return ServerResponse.createBySuccess(bankCardInfo);
            else
                return ServerResponse.createByError();
        } else
            return ServerResponse.createByError();
    }

    /**
     * 获取所有的银行信息
     *
     * @return
     */
    @RequestMapping("/findAllBanks")
    @ResponseBody
    public ServerResponse findAllBanks() {
        List<Bank> banks = bankService.findAll();
        return ServerResponse.createBySuccess(banks);
    }

    /**
     * 获取所有的省
     *
     * @return
     */
    @RequestMapping("/findProvince")
    @ResponseBody
    public ServerResponse findProvince() {
        List<City> provinces = cityService.findProvince();
        return ServerResponse.createBySuccess(provinces);
    }

    /**
     * 获取市/区
     *
     * @param cityAreaNum 上级编号
     * @return
     */
    @RequestMapping("/findCity")
    @ResponseBody
    public ServerResponse findCity(String cityAreaNum) {
        if (cityAreaNum == null)
            return ServerResponse.createByError();

        List<City> cities = cityService.findCity(cityAreaNum);
        return ServerResponse.createBySuccess(cities);
    }

    /**
     * 获取银行卡用户信息
     *
     * @param username 用户名
     * @return
     */
    @RequestMapping("/findUserInfo")
    @ResponseBody
    public ServerResponse findUserInfo(@RequestHeader(name = "token") String token, String username) {
        if (StringUtils.isEmpty(token))
            return ServerResponse.createByError(ResponseCode.NULL_TOKEN.getCode());

        Map<String, Object> userMap = baseCacheService.getHmap(token);
        if (username == null || userMap == null || userMap.size() == 0)
            return ServerResponse.createByError(ResponseCode.LOGIN_INVALID.getCode());

        String _username = (String) userMap.get("username");
        if (username.startsWith(_username)) {
            int userId = (int) userMap.get("id");
            User user = userService.findById(userId);
            return ServerResponse.createBySuccess(user);
        } else
            return ServerResponse.createByError();
    }

    /**
     * 添加银行卡
     * @param bankCardInfo
     * @param token
     * @return
     */
    @RequestMapping("/addBankCardInfo")
    @ResponseBody
    public ServerResponse addBankCardInfo(BankCardInfo bankCardInfo, @RequestHeader(value = "token") String token) {
        if (StringUtils.isEmpty(token))
            return ServerResponse.createByError(ResponseCode.NULL_TOKEN.getCode());

        Map<String, Object> userMap = baseCacheService.getHmap(token);
        if (userMap == null || userMap.size() == 0)
            return ServerResponse.createByError(ResponseCode.LOGIN_INVALID.getCode());

        int userId = (int) userMap.get("id");
        BankCardInfo _bankCardInfo = bankCardInfoService.findByUserId(userId);
        if (_bankCardInfo == null) {
            //可以绑定
            bankCardInfo.setUserId(userId);
            bankCardInfoService.addBankCardInfo(bankCardInfo);
            return ServerResponse.createBySuccess();
        } else
            return ServerResponse.createByError(ResponseCode.CARDINFO_ALEARD_EXIST.getCode());
    }

}
