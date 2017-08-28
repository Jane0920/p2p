package com.xyr.service.impl;

import com.xyr.dao.AccountDAO;
import com.xyr.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyr on 2017/8/28.
 */
@Service
public class AccountServiceImpl implements com.xyr.service.AccountService {

    @Autowired
    private AccountDAO accountDAO;

    @Override
    public Admin login(String username, String password) {

        return accountDAO.findByUsernameAndPassword(username, password);
    }
}
