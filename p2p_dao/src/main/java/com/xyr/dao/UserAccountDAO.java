package com.xyr.dao;

import com.xyr.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xyr on 2017/9/1.
 */
public interface UserAccountDAO extends JpaRepository<UserAccount, Integer> {

    UserAccount findByUserId(int userId);

}
