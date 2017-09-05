package com.xyr.service.impl;

import com.xyr.dao.UserDAO;
import com.xyr.domain.User;
import com.xyr.service.UserService;
import com.xyr.utils.MD5Util;
import com.xyr.utils.ResponseCode;
import com.xyr.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ServerResponse validatePhone(String phone, Integer id) {
        User user = userDAO.findByPhone(phone);
        if (id == null) {
            if (user == null)
                return ServerResponse.createBySuccess();
            else
                return ServerResponse.createByError(ResponseCode.PHONE_ALREADY_REGISTER.getCode());
        } else {
            if (user == null || user.getId() != id)
                return ServerResponse.createByError();
            else
                return ServerResponse.createByError(ResponseCode.PHONE_ALREADY_REGISTER.getCode());
        }
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

    @Override
    public User findByPhone(String phone) {
        return userDAO.findByPhone(phone);
    }

    public User login(String username, String password) {
        String pwd = MD5Util.md5(username.trim() + password.trim().toLowerCase());
        return userDAO.findByUsernameAndPassword(username, pwd);
    }

    @Override
    @Transactional
    public ServerResponse updateRealName(String realName, String identity, int id) {
        userDAO.updateRealName(realName, identity, id);
        return ServerResponse.createBySuccess();
    }

    @Override
    @Transactional
    public ServerResponse updatePhoneAndPhoneStatus(String phone, int phoneStatus, int id) {

        userDAO.updatePhoneAndPhoneStatus(phone, phoneStatus, id);
        return ServerResponse.createBySuccess();
    }
}
