package com.xyr.service.impl;

import com.xyr.dao.WeightRuleDAO;
import com.xyr.domain.WeigthRule;
import com.xyr.service.WeigthRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyr on 2017/9/12.
 */
@Service
public class WeigthRuleServiceImpl implements WeigthRuleService {

    @Autowired
    private WeightRuleDAO weightRuleDAO;

    @Override
    public WeigthRule findByWeigthType(int type) {
        return weightRuleDAO.findByWeigthType(type);
    }
}
