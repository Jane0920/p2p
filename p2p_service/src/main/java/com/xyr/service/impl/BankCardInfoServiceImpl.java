package com.xyr.service.impl;

import com.xyr.dao.BankCardInfoDAO;
import com.xyr.domain.BankCardInfo;
import com.xyr.service.BankCardInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyr on 2017/9/8.
 */
@Service
public class BankCardInfoServiceImpl implements BankCardInfoService {

    @Autowired
    private BankCardInfoDAO bankCardInfoDAO;

    @Override
    public BankCardInfo findByUserId(int userId) {
        return bankCardInfoDAO.findByUserId(userId);
    }

}
