package com.xyr.service;

import com.xyr.utils.ServerResponse;

/**
 * Created by xyr on 2017/8/29.
 */
public interface ProductService {

    ServerResponse findAllProduct();

    ServerResponse findProductById(long proId);

    ServerResponse findRates(String proId);

}
