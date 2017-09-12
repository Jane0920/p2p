package com.xyr.service.impl;

import com.xyr.dao.ExpectedReturnDAO;
import com.xyr.domain.ExpectedReturn;
import com.xyr.service.ExpectedReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyr on 2017/9/12.
 */
@Service
public class ExpectedReturnServiceImpl implements ExpectedReturnService {

    @Autowired
    private ExpectedReturnDAO expectedReturnDAO;

    @Override
    public void add(ExpectedReturn expectedReturn) {
        expectedReturnDAO.saveAndFlush(expectedReturn);
    }
}
