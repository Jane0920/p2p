package com.xyr.service.impl;

import com.xyr.dao.UserDAO;
import com.xyr.domain.User;
import com.xyr.service.UserService;
import com.xyr.utils.ResponseCode;
import com.xyr.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyr on 2017/9/1.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public ServerResponse validateUserName(String username) {
        User user = userDAO.findByUsername(username);
        if (user == null)
            return ServerResponse.createBySuccess();
        else
            return ServerResponse.createByError(ResponseCode.ALREADY_EXIST_OF_USERNAME.getCode());
    }

    @Override
    public ServerResponse validatePhone(String phone) {
        User user = userDAO.findByPhone(phone);
        if (user == null)
            return ServerResponse.createBySuccess();
        else
            return ServerResponse.createByError(ResponseCode.PHONE_ALREADY_REGISTER.getCode());
    }

    @Override
    public boolean addUser(User user) {
        userDAO.saveAndFlush(user);
        return true;
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }
}
