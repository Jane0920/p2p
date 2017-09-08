package com.xyr.service;

import com.xyr.domain.ProductEarningRate;

import java.util.List;

/**
 * Created by xyr on 2017/9/8.
 */
public interface ProductRateService {

    List<ProductEarningRate> findByProId(String proId);

}
