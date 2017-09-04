package com.xyr.service;

import com.xyr.domain.UserAccount;

/**
 * Created by xyr on 2017/9/1.
 */
public interface UserAccountService {

    void addUserAccount(int userId);

    UserAccount findByUserId(int userId);

}
