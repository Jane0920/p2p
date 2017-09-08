package com.xyr.service;

import com.xyr.domain.BankCardInfo;

import java.util.List;

/**
 * Created by xyr on 2017/9/8.
 */
public interface BankCardInfoService {

    BankCardInfo findByUserId(int userId);

}
