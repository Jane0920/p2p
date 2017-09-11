package com.xyr.service.impl;

import com.xyr.dao.UserAccountDAO;
import com.xyr.domain.UserAccount;
import com.xyr.service.UserAccountService;
import com.xyr.utils.ResponseCode;
import com.xyr.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public ServerResponse updateBalanceAndTotal(double chargeMoney, int userId) {
        UserAccount userAccount = this.findByUserId(userId);
        if (userAccount == null)
            return ServerResponse.createByError(ResponseCode.NO_USER.getCode());

        userAccountDAO.updateBalanceAndTotal(userAccount.getTotal() + chargeMoney,
                userAccount.getBalance() + chargeMoney, userId);
        return ServerResponse.createBySuccess(userAccount.getBalance() + chargeMoney);
    }
}
