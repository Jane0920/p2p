package com.xyr.dao;

import com.xyr.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xyr on 2017/8/29.
 */
public interface ProductDAO extends JpaRepository<Product, Long> {
}
