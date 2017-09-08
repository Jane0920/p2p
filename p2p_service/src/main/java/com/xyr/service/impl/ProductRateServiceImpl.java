package com.xyr.service.impl;

import com.xyr.dao.ProductEarningRateDAO;
import com.xyr.domain.ProductEarningRate;
import com.xyr.service.ProductRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyr on 2017/9/8.
 */
@Service
public class ProductRateServiceImpl implements ProductRateService {

    @Autowired
    private ProductEarningRateDAO productEarningRateDAO;

    @Override
    public List<ProductEarningRate> findByProId(String proId) {
        return productEarningRateDAO.findByProductId(Integer.parseInt(proId));
    }
}
