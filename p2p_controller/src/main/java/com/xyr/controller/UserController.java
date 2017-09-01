package com.xyr.controller;

import com.google.common.collect.Maps;
import com.xyr.cache.BaseCacheService;
import com.xyr.domain.User;
import com.xyr.service.UserAccountService;
import com.xyr.service.UserService;
import com.xyr.utils.ConfigurableConstants;
import com.xyr.utils.ImageUtil;
import com.xyr.utils.MD5Util;
import com.xyr.utils.ResponseCode;
import com.xyr.utils.ServerResponse;
import com.xyr.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xyr on 2017/8/31.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private BaseCacheService baseCacheService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserAccountService userAccountService;

    /**
     * 获取验证码，并且保存到redis中
     * @return
     */
    @RequestMapping("/uuid")
    @ResponseBody
    public ServerResponse getUuid() {
        String uuid = UUID.randomUUID().toString();
        baseCacheService.set(uuid, uuid);
        baseCacheService.expire(uuid, 3 * 60);//设置三分钟有效

        return ServerResponse.createBySuccess(uuid);
    }

    /**
     * 获取图片验证码
     *
     * @param tokenUuid uuid对应的redis key
     * @return
     */
    @RequestMapping("/validateCode")
    public void validateCode(String tokenUuid, HttpServletResponse response) {
        String uuid = baseCacheService.get(tokenUuid);
        if (StringUtils.isEmpty(uuid))
            return;

        //产生图片验证码
        //通过工具获取验证码信息
        String str = ImageUtil.getRundomStr();
        //将验证码信息存取到原来uuid对应的值中
        baseCacheService.del(tokenUuid);
        baseCacheService.set(tokenUuid, str);
        baseCacheService.expire(tokenUuid, 3 * 60);
        //将验证码图片相应到服务器
        try {
            ImageUtil.getImage(str, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证用户名是否已存在
     *
     * @param username
     * @return
     */
    @RequestMapping("/validateUserName")
    @ResponseBody
    public ServerResponse validateUserName(String username) {
        return userService.validateUserName(username);
    }

    /**
     * 验证手机号是否已被注册
     *
     * @param phone
     * @return
     */
    @RequestMapping("/validatePhone")
    @ResponseBody
    public ServerResponse validatePhone(String phone) {
        return userService.validatePhone(phone);
    }

    /**
     * 检验验证码是否输入正确
     *
     * @param signUuid redis中验证码存放的key(uuid)
     * @param signCode 输入的验证码
     * @return
     */
    @RequestMapping("/codeValidate")
    @ResponseBody
    public ServerResponse codeValidate(String signUuid, String signCode) {
        String str = baseCacheService.get(signUuid);

        if (str.equalsIgnoreCase(signCode))
            return ServerResponse.createBySuccess();
        else
            return ServerResponse.createByError(ResponseCode.INPUT_ERROR_OF_VALIDATE_CODE.getCode());
    }

    /**
     * 注册用户：在数据库中添加用户及用户账号
     *
     * @param user
     * @return
     */
    @RequestMapping("/signup")
    @ResponseBody
    public ServerResponse signUp(User user, String signUuid, String signCode) {

        //空检验
        if (StringUtils.isEmpty(signUuid))
            return ServerResponse.createByError(ResponseCode.NULL_TOKEN.getCode());
        if (StringUtils.isEmpty(signCode))
            return ServerResponse.createByError(ResponseCode.NULL_OF_VALIDATE_CODE.getCode());
        String _signCode = baseCacheService.get(signUuid);
        if (StringUtils.isEmpty(_signCode))
            return ServerResponse.createByError(ResponseCode.NULL_OF_VALIDATE_CODE.getCode());

        //检验输入验证码是否正确
        if (!_signCode.equalsIgnoreCase(signCode))
            return ServerResponse.createByError(ResponseCode.INPUT_ERROR_OF_VALIDATE_CODE.getCode());

        String pwd = MD5Util.md5(user.getUsername().trim() + user.getPassword().trim().toLowerCase());
        user.setPassword(pwd);
        user.setRegisterTime(new Date());
        boolean flag = userService.addUser(user);
        if (flag) {
            //生成令牌
            String token = generateUserToken(user.getUsername());
            Map<String, Object> map = baseCacheService.getHmap(token);
            int userId = Integer.parseInt(map.get("id").toString());
            userAccountService.addUserAccount(userId);

            Map<String, Object> returnMap = Maps.newHashMap();
            returnMap.put("id", userId);
            returnMap.put("token", token);
            return ServerResponse.createBySuccess(returnMap);
        } else
            return ServerResponse.createByError(ResponseCode.ADD_FALIUE.getCode());
    }

    /**
     * 根据username生成令牌，并且以哈希的形式将用户信息保存到redis中，作为session
     *
     * @param username
     * @return
     */
    private String generateUserToken(String username) {
        //生成令牌
        String token = TokenUtil.generateUserToken(username);

        User user = userService.findByUsername(username);
        Map<String, Object> tokenMap = Maps.newHashMap();
        tokenMap.put("id", user.getId());
        tokenMap.put("username", user.getUsername());
        tokenMap.put("phone", user.getPhone());
        tokenMap.put("userType", user.getUserType());
        tokenMap.put("payPwdStatus", user.getPayPwdStatus());
        tokenMap.put("realNameStatus", user.getRealNameStatus());
        tokenMap.put("emailStatus", user.getEmailStatus());
        tokenMap.put("realName", user.getRealName());
        tokenMap.put("identity", user.getIdentity());
        tokenMap.put("phoneStatus", user.getPhoneStatus());

        baseCacheService.del(token);
        baseCacheService.setHmap(token, tokenMap);
        //获取用户的生命周期，默认为
        String tokenValidity = ConfigurableConstants.getProperty("token.validity", "30");
        tokenValidity = tokenValidity.trim();
        baseCacheService.expire(token, Long.valueOf(tokenValidity) * 60);

        return token;
    }

}
