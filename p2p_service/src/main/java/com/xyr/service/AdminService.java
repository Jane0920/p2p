package com.xyr.service;

import com.xyr.domain.Admin;

/**
 * Created by xyr on 2017/8/28.
 */
public interface AdminService {

    /**
     * 登录验证
     *
     * @param username
     * @param password
     * @return
     */
    Admin login(String username, String password);

}
