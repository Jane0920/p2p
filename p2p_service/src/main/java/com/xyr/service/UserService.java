package com.xyr.service;

import com.xyr.domain.User;
import com.xyr.utils.ServerResponse;

/**
 * Created by xyr on 2017/9/1.
 */
public interface UserService {

    ServerResponse validateUserName(String username);

    ServerResponse validatePhone(String phone);

    boolean addUser(User user);

    User findByUsername(String username);

    User findByPhone(String phone);

    User login(String username, String password);

}
