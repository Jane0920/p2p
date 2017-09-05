package com.xyr.controller;

import com.xyr.service.EmailService;
import com.xyr.service.UserService;
import com.xyr.utils.EmailUtils;
import com.xyr.utils.ResponseCode;
import com.xyr.utils.SecretUtil;
import com.xyr.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xyr on 2017/9/5.
 */
@Controller
@RequestMapping("/emailAuth")
public class EmailAuthController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;

    /**
     * 邮箱认证
     *
     * @param userId
     * @param username
     * @param email
     * @return
     */
    @RequestMapping("/auth")
    @ResponseBody
    public ServerResponse auth(@RequestHeader(value = "token") String token, int userId, String username, String email) {
        if (StringUtils.isEmpty(token))
            return ServerResponse.createByError(ResponseCode.NULL_TOKEN.getCode());
        //设置标题
        String title = "p2p邮箱认证";

        try {
            //加密用户id
            String sec = SecretUtil.encrypt(userId + "");
            //获取内容
            String content = EmailUtils.getMailCapacity(email, sec, username);
            //发送邮件
            emailService.sendEmail(email, title, content);
            //添加邮箱
            return userService.updateEmailAndEmailStatus(email, 0, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByError();
        }
    }

}
