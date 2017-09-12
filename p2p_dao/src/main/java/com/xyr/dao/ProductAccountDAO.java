package com.xyr.dao;

import com.xyr.domain.ProductAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xyr on 2017/9/12.
 */
public interface ProductAccountDAO extends JpaRepository<ProductAccount, Integer> {
}
