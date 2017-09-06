package com.xyr.service.impl;

import com.google.common.collect.Maps;
import com.xyr.service.EmailService;
import com.xyr.utils.IMessage;
import com.xyr.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mail.MailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * Created by xyr on 2017/9/5.
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * 发送邮件
     *
     * @param email   目标邮件地址
     * @param title   邮件标题
     * @param content 邮件内容
     * @return
     */
    public void sendEmail(String email, String title, String content) {
        Map<String, Object> map = Maps.newHashMap();

        map.put(IMessage.MessageType, IMessage.EmailMessage);
        map.put(IMessage.MessageContent, content);
        map.put(IMessage.EmailMessageTitle, title);
        map.put(IMessage.EmailMessageTo, email);

        //通过convertAndSend调用消息转换器messageConverter，然后发送
        jmsTemplate.convertAndSend(map);

//        //创建邮件信息
//        MimeMessage mm = javaMailSender.createMimeMessage();
//
//        MimeMessageHelper helper = new MimeMessageHelper(mm);
//        try {
//            helper.setFrom("736845367@qq.com");
//            helper.setSubject(title);
//            helper.setText(content, true); //true表示如果内容包含html代码则解析
//            helper.setTo(email);
//            javaMailSender.send(mm);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }

    }

}
