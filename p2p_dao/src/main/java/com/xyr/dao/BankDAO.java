package com.xyr.dao;

import com.xyr.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xyr on 2017/9/8.
 */
public interface BankDAO extends JpaRepository<Bank, Integer> {


}
