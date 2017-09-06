package com.xyr.service.msm;

/**
 * Created by Administrator on 2017/9/6.
 */
public interface MSMService {

    /**
     * 完成向activityMQ发送要发送的短信
     * @param phone
     * @param content
     */
    public void sendMsm(String phone, String content);

}
