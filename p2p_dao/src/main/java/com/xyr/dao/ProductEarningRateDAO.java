package com.xyr.dao;

import com.xyr.domain.ProductEarningRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by xyr on 2017/8/29.
 */
public interface ProductEarningRateDAO extends JpaRepository<ProductEarningRate, Integer> {

    List<ProductEarningRate> findByProductId(int productId);

}
