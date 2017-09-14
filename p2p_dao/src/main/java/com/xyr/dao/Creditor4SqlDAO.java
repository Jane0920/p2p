package com.xyr.dao;

import com.xyr.domain.CreditorModel;

import java.util.List;
import java.util.Map;

/**
 * Created by xyr on 2017/9/14.
 */
public interface Creditor4SqlDAO {

    List<CreditorModel> findCreditorByCondition(Map<String, Object> map);

    List<Object[]> findCreditorBySum(Map<String, Object> map);

}
