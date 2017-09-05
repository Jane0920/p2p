package com.xyr.service;

import com.xyr.utils.ServerResponse;

/**
 * Created by xyr on 2017/9/5.
 */
public interface EmailService {

    void sendEmail(String email, String title, String content);

}
