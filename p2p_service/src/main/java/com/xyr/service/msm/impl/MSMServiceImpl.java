package com.xyr.service.msm.impl;

import com.google.common.collect.Maps;
import com.xyr.service.msm.MSMService;
import com.xyr.utils.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/6.
 */
@Service
public class MSMServiceImpl implements MSMService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sendMsm(String phone, String content) {
        Map<String, Object> map = Maps.newHashMap();

        map.put(IMessage.MessageType, IMessage.SMSMessage);
        map.put(IMessage.MessageContent, content);
        map.put(IMessage.SMSNumbers, phone);

        jmsTemplate.convertAndSend(map);

    }
}
