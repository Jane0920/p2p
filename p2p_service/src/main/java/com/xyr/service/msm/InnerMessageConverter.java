package com.xyr.service.msm;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/6.
 */
public class InnerMessageConverter implements MessageConverter{
    @Override
    public Message toMessage(Object o, Session session) throws JMSException, MessageConversionException {
        MapMessage message = session.createMapMessage();

        @SuppressWarnings("all")
        Map<String, Object> map = (Map) o;

        message.setObject("title", map.get("title"));
        message.setObject("content", map.get("content"));
        message.setObject("to", map.get("to"));
        message.setObject("cc", map.get("cc"));
        message.setObject("type", map.get("type"));

        return message;
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        return message;
    }
}
