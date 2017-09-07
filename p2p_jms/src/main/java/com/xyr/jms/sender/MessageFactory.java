package com.xyr.jms.sender;

import com.xyr.jms.MessageReceiver;
import com.xyr.jms.vo.MessageConstant;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by xyr on 2017/9/7.
 */
@Component
public class MessageFactory {

    @Resource(name = "emailSender")
    private MessageSender email;
    @Resource(name = "smsSender")
    private MessageSender sms;

    /**
     * 根据消息类型创建具体的消息发送对象
     *
     * @param type
     * @return
     */
    public MessageSender getMessageSender(String type) {
        if (MessageConstant.EmailMessage.equals(type))
            return this.email;
        else if (MessageConstant.SMSMessage.equals(type))
            return this.sms;
        else
            return null;
    }

}
