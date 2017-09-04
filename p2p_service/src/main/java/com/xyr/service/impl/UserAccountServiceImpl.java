package com.xyr.service.impl;

import com.xyr.dao.UserAccountDAO;
import com.xyr.dao.UserDAO;
import com.xyr.domain.User;
import com.xyr.domain.UserAccount;
import com.xyr.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyr on 2017/9/1.
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountDAO userAccountDAO;

    @Override
    public void addUserAccount(int userId) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(userId);
        userAccountDAO.saveAndFlush(userAccount);
    }

    @Override
    public UserAccount findByUserId(int userId) {
        return userAccountDAO.findByUserId(userId);
    }
}
