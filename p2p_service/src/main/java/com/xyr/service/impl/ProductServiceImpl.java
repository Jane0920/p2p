package com.xyr.service.impl;

import com.xyr.dao.ProductDAO;
import com.xyr.dao.ProductEarningRateDAO;
import com.xyr.domain.Product;
import com.xyr.domain.ProductEarningRate;
import com.xyr.service.ProductService;
import com.xyr.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyr on 2017/8/29.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private ProductEarningRateDAO productEarningRateDAO;

    @Override
    public ServerResponse findAllProduct() {
        List<Product> productList = productDAO.findAll();
        return ServerResponse.createBySuccess(productList);
    }

    @Override
    public ServerResponse findProductById(long proId) {
        Product product = productDAO.findOne(proId);
        return ServerResponse.createBySuccess(product);
    }

    public ServerResponse findRates(String proId) {
        List<ProductEarningRate> productEarningRates = productEarningRateDAO.findByProductId(Integer.parseInt(proId));
        return ServerResponse.createBySuccess(productEarningRates);
    }
}
