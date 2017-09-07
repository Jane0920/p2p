package com.xyr.jms;

import com.xyr.jms.sender.MessageFactory;
import com.xyr.jms.sender.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by xyr on 2017/9/7.
 */
public class MessageReceiver implements MessageListener {

    private static final Logger log = LoggerFactory.getLogger(MessageReceiver.class);
    @Autowired
    private MessageFactory messageFactory;

    /**
     * 监听activityMQ中是否存在消息，如果存在则执行
     *
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        if (message instanceof MapMessage) {
            MapMessage map = (MapMessage) message;
            try {
                processMessage(map);// 调用发送消息
                message.acknowledge(); // 自动通知确认 手动接收
            } catch (Exception e) {
                log.error("监听异常：" + e);
            }
        }
    }

    /**
     * 处理信息
     *
     * @param mapMessage
     */
    private void processMessage(MapMessage mapMessage) {
        try {
            String type = mapMessage.getString("type");
            MessageSender sender = messageFactory.getMessageSender(type);
            sender.sendMessage(mapMessage);
        } catch (Exception e) {
            log.error("processMessage发送消息异常：" + e);
        }
    }
}
