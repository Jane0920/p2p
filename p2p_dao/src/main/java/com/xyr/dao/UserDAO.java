package com.xyr.dao;

import com.xyr.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by xyr on 2017/9/1.
 */
public interface UserDAO extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByPhone(String phone);

    User findByUsernameAndPassword(String username, String password);

    @Modifying
    @Query("update User set realName = ?1, identity = ?2, realNameStatus = 1 where id = ?3")
    void updateRealName(String realName, String identity, int id);

}
