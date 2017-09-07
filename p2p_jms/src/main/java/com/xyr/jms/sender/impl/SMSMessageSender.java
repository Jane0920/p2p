package com.xyr.jms.sender.impl;

import com.xyr.jms.sender.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import java.util.List;

/**
 * Created by xyr on 2017/9/7.
 */
@Component("smsSender")
public class SMSMessageSender implements MessageSender {

    private static final Logger log = LoggerFactory.getLogger(SMSMessageSender.class);

    @Override
    public void sendMessage(MapMessage message) throws Exception {
        log.info("发送短信：" + message);
    }
}
