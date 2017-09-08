package com.xyr.dao;

import com.xyr.domain.BankCardInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xyr on 2017/9/8.
 */
public interface BankCardInfoDAO extends JpaRepository<BankCardInfo, Integer> {

    BankCardInfo findByUserId(int userId);

}
