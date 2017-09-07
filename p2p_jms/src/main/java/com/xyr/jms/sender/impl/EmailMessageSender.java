package com.xyr.jms.sender.impl;

import com.xyr.jms.MessageReceiver;
import com.xyr.jms.sender.MessageSender;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;

/**
 * Created by xyr on 2017/9/7.
 */
@Component("emailSender")
public class EmailMessageSender implements MessageSender {
    @Override
    public void sendMessage(MapMessage message) throws Exception {

    }
}
