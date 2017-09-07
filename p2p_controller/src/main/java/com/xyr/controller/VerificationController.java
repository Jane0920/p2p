package com.xyr.controller;

import com.sun.org.apache.regexp.internal.RE;
import com.xyr.cache.BaseCacheService;
import com.xyr.domain.User;
import com.xyr.service.UserService;
import com.xyr.service.msm.MSMService;
import com.xyr.utils.ResponseCode;
import com.xyr.utils.SecretUtil;
import com.xyr.utils.ServerResponse;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(VerificationController.class);

    @Autowired
    private BaseCacheService baseCacheService;
    @Autowired
    private UserService userService;
    @Autowired
    private MSMService msmService;

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

    /**
     * 发送手机认证验证码
     *
     * @param phone
     * @return
     */
    @RequestMapping("/sendMessage")
    @ResponseBody
    public ServerResponse sendMessage(String phone) {
        //获取随机验证码
        String phoneCode = RandomStringUtils.randomNumeric(6);
        String msg = "p2p手机验证码：" + phoneCode;

        //模拟发送短信
        logger.info(msg + "..................");
        msmService.sendMsm(phone, msg);

        //保存验证码到redis
        baseCacheService.set(phone, phoneCode);
        baseCacheService.expire(phone, 3 * 60); //验证码三分钟后失效

        return ServerResponse.createBySuccess();

    }

    /**
     * 进行手机认证
     *
     * @param token
     * @param phone
     * @param phoneCode
     * @return
     */
    @RequestMapping("/addPhone")
    @ResponseBody
    public ServerResponse addPhone(@RequestHeader(value = "token") String token, String phone, String phoneCode) {
        if (StringUtils.isEmpty(token))
            return ServerResponse.createByError(ResponseCode.NULL_TOKEN.getCode());

        Map<String, Object> userMap = baseCacheService.getHmap(token);
        if (userMap == null || userMap.size() == 0)
            return ServerResponse.createByError(ResponseCode.LOGIN_INVALID.getCode());

        //判断输入验证码是否正确
        String _phoneCode = baseCacheService.get(phone);
        if (!phoneCode.equals(_phoneCode))
            return ServerResponse.createByError(ResponseCode.INPUT_ERROR_OF_VALIDATE_CODE.getCode());

        //判断用户手机号是否已经验证
        User user = userService.findByUsername((String) userMap.get("username"));
        if (user.getPhoneStatus() == 1)
            return ServerResponse.createByError(ResponseCode.PHONE_ALREADY_REGISTER.getCode());

        //更新手机认证状态
        return userService.updatePhoneAndPhoneStatus(phone, 1, user.getId());
    }

    /**
     * 邮箱激活
     *
     * @param us 加密后的id
     * @return
     */
    @RequestMapping("/emailActivation")
    //@ResponseBody
    public String emailActivation(String us) {
        try {
            //解密id
            int id = Integer.parseInt(SecretUtil.decode(us));
            //获取user，校验邮箱是否已经绑定
            User user = userService.findById(id);
            if (user == null || StringUtils.isEmpty(user.getEmail()))
                return "return";

            //修改email的状态为已认证
            userService.updateEmailStatus(1, id);
            return "return";
        } catch (Exception e) {
            e.printStackTrace();
            return "return";
        }
    }

}
