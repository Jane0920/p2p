package com.xyr.service.impl;

import com.xyr.dao.BankDAO;
import com.xyr.domain.Bank;
import com.xyr.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyr on 2017/9/8.
 */
@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankDAO bankDAO;

    @Override
    public List<Bank> findAll() {
        return bankDAO.findAll();
    }
}
