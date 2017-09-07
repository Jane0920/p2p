package com.xyr.jms.sender;

import com.xyr.jms.MessageReceiver;
import com.xyr.jms.vo.MessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by xyr on 2017/9/7.
 */
@Component
public class MessageFactory {

    @Autowired
    private MessageSender emailSender;
    @Autowired
    private MessageSender smsSender;

    /**
     * 根据消息类型创建具体的消息发送对象
     *
     * @param type
     * @return
     */
    public MessageSender getMessageSender(String type) {
        if (MessageConstant.EmailMessage.equals(type))
            return this.emailSender;
        else if (MessageConstant.SMSMessage.equals(type))
            return this.smsSender;
        else
            return null;
    }

}
