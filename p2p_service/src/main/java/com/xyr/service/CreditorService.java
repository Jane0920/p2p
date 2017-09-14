package com.xyr.service;

import com.xyr.domain.CreditorModel;

import java.util.List;
import java.util.Map;

/**
 * Created by xyr on 2017/9/13.
 */
public interface CreditorService {

    void addCreditor(CreditorModel creditorModel);

    void addCreditor(List<CreditorModel> creditorModels);

    List<CreditorModel> findCreditorByCondition(Map<String, Object> map);

    List<Object[]> findCreditorBySum(Map<String, Object> map);

    void checkCreditor(String[] id);
}
