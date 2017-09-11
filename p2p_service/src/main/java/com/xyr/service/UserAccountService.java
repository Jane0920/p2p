package com.xyr.service;

import com.xyr.domain.UserAccount;
import com.xyr.utils.ServerResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xyr on 2017/9/1.
 */
public interface UserAccountService {

    void addUserAccount(int userId);

    UserAccount findByUserId(int userId);

    @Transactional
    ServerResponse updateBalanceAndTotal(double chargeMoney, int userId);

}
