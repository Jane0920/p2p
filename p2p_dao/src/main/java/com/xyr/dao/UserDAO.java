package com.xyr.dao;

import com.xyr.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xyr on 2017/9/1.
 */
public interface UserDAO extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByPhone(String phone);

    User findByUsernameAndPassword(String username, String password);

}
