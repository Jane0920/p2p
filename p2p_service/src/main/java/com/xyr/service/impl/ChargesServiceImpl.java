package com.xyr.service.impl;

import com.xyr.service.ChargesService;
import org.springframework.stereotype.Service;

/**
 * Created by xyr on 2017/9/11.
 */
@Service
public class ChargesServiceImpl implements ChargesService {

    @Override
    public void reCharge(double money, String bankCardNum) {
        //todo webservice 调用服务端实现银行转账
    }
}
