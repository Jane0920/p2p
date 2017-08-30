package com.xyr.service;

import com.xyr.domain.Product;
import com.xyr.utils.ServerResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xyr on 2017/8/29.
 */
public interface ProductService {

    ServerResponse findAllProduct();

    ServerResponse findProductById(long proId);

    ServerResponse findRates(String proId);

    @Transactional
    ServerResponse updateProduct(Product product);

    ServerResponse addProduct(Product product);

    @Transactional
    ServerResponse productDel(String proId);

}
