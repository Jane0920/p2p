package com.xyr.dao;

import com.xyr.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xyr on 2017/8/28.
 */
public interface AdminDAO extends JpaRepository<Admin, Integer> {

    Admin findByUsernameAndPassword(String username, String password);

}
