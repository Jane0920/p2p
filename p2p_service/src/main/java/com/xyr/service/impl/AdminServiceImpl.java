package com.xyr.service.impl;

import com.xyr.dao.AdminDAO;
import com.xyr.domain.Admin;
import com.xyr.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyr on 2017/8/28.
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDAO adminDAO;

    @Override
    public Admin login(String username, String password) {

        return adminDAO.findByUsernameAndPassword(username, password);
    }
}
