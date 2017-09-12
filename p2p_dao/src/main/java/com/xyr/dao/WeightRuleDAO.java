package com.xyr.dao;

import com.xyr.domain.WeigthRule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xyr on 2017/9/12.
 */
public interface WeightRuleDAO extends JpaRepository<WeigthRule, Integer> {

    WeigthRule findByWeigthType(int type);

}
