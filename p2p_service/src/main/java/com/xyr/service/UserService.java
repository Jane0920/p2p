package com.xyr.service;

import com.xyr.domain.User;
import com.xyr.utils.ServerResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xyr on 2017/9/1.
 */
public interface UserService {

    ServerResponse validateUserName(String username);

    ServerResponse validatePhone(String phone, Integer id);

    boolean addUser(User user);

    User findByUsername(String username);

    User findByPhone(String phone);

    User login(String username, String password);

    @Transactional
    ServerResponse updateRealName(String realName, String identity, int id);

    @Transactional
    ServerResponse updatePhoneAndPhoneStatus(String phone, int phoneStatus, int id);

}
