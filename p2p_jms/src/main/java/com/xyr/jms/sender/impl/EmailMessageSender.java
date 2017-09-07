package com.xyr.jms.sender.impl;

import com.xyr.jms.Converter;
import com.xyr.jms.IMessage;
import com.xyr.jms.MessageReceiver;
import com.xyr.jms.sender.MessageSender;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by xyr on 2017/9/7.
 */
@Component("emailSender")
public class EmailMessageSender implements MessageSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMessage(MapMessage message) {
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
        MimeMessage mime = javaMailSender.createMimeMessage();
        MimeMessageHelper mimehelper = null;
        try {
            mimehelper = new MimeMessageHelper(mime, true);
            String from = message.getString(IMessage.EmailMessageFrom);
            if (StringUtils.isEmpty(from))
                from = "736845367@qq.com";
            // 设置发送人
            mimehelper.setFrom(from);
            // 设置主题
            String title = message.getString(IMessage.EmailMessageTitle);
            mimehelper.setSubject(StringUtils.isEmpty(title) ? "" : title);
            // 设置发送日期
            mimehelper.setSentDate(new Date());
            // 设置邮件内容为HTML超文本格式
            mimehelper.setText(message.getString(IMessage.MessageContent), true);
            // 设置收件人
            InternetAddress[] addrs = this.getAddr(message.getObject(IMessage.EmailMessageTo));
            if (null == addrs || 0 == addrs.length) {
                throw new RuntimeException("Message is Null");
            }
            mimehelper.setTo(addrs);
            // 设置抄送人
            addrs = this.getAddr(message.getObject(IMessage.EmailMessageCC));
            if (null != addrs && addrs.length > 0) {
                mimehelper.setCc(addrs);
            }
            // 设置暗送
            addrs = this.getAddr(message.getObject(IMessage.EmailMessageBCC));
            if (null != addrs && addrs.length > 0) {
                mimehelper.setBcc(addrs);
            }
            javaMailSender.send(mime);// 将邮件发送
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private InternetAddress[] getAddr(Object addr) throws AddressException {
        List<String> addrList = Converter.converter(addr);
        InternetAddress[] internetAddressArry = new InternetAddress[addrList.size()];
        int size = 0;
        for (String string : addrList) {
            if (StringUtils.isEmpty(string)) {
                continue;
            }
            internetAddressArry[size++] = new InternetAddress(string);
        }
        return 0 == size ? null : Arrays.copyOfRange(internetAddressArry, 0, size);
    }
}
