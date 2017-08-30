package com.xyr.service.impl;

import com.google.common.collect.Lists;
import com.xyr.dao.ProductDAO;
import com.xyr.dao.ProductEarningRateDAO;
import com.xyr.domain.Product;
import com.xyr.domain.ProductEarningRate;
import com.xyr.service.ProductService;
import com.xyr.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Override
    public ServerResponse findRates(String proId) {
        List<ProductEarningRate> productEarningRates = productEarningRateDAO.findByProductIdOrderByMonth(Integer.parseInt(proId));
        return ServerResponse.createBySuccess(productEarningRates);
    }

    @Override
    @Transactional
    public ServerResponse updateProduct(Product product) {
        List<ProductEarningRate> productEarningRates = product.getProEarningRate();
        if (productEarningRates != null && productEarningRates.size() > 0)
            productEarningRateDAO.deleteByProductId((int) product.getProId());

        productDAO.saveAndFlush(product);
        productEarningRateDAO.save(productEarningRates);

        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse addProduct(Product product) {
        Product nowProduct = productDAO.saveAndFlush(product);

        List<ProductEarningRate> rates = product.getProEarningRate();
        if (rates != null && rates.size() > 0) {
            List<ProductEarningRate> nowRates = Lists.newArrayList();
            for (ProductEarningRate rate : rates) {
                rate.setProductId((int) nowProduct.getProId());
                nowRates.add(rate);
            }

            productEarningRateDAO.save(nowRates);
        }

        return ServerResponse.createBySuccess();
    }

    @Override
    @Transactional
    public ServerResponse productDel(String proId) {
        productEarningRateDAO.deleteByProductId(Integer.parseInt(proId));
        productDAO.delete(Long.parseLong(proId));
        return ServerResponse.createBySuccess();
    }
}
