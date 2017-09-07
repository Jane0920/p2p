package com.xyr.jms.sender;

import javax.jms.MapMessage;

/**
 * Created by xyr on 2017/9/7.
 * 发送消息类
 */
public interface MessageSender {

    void sendMessage(MapMessage message) throws Exception;

}
