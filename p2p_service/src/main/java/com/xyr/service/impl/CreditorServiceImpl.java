package com.xyr.service.impl;

import com.xyr.dao.Creditor4SqlDAO;
import com.xyr.dao.CreditorDAO;
import com.xyr.domain.CreditorModel;
import com.xyr.service.CreditorService;
import com.xyr.utils.ClaimsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xyr on 2017/9/13.
 */
@Service
public class CreditorServiceImpl implements CreditorService {

    @Autowired
    private CreditorDAO creditorDAO;
    @Autowired
    private Creditor4SqlDAO creditor4SqlDAO;

    @Override
    public void addCreditor(CreditorModel creditorModel) {
        creditorDAO.saveAndFlush(creditorModel);
    }

    @Override
    public void addCreditor(List<CreditorModel> creditorModels) {
        creditorDAO.save(creditorModels);
    }

    // 多条件债权信息查询
    @Override
    public List<CreditorModel> findCreditorByCondition(Map<String, Object> map) {
        return creditor4SqlDAO.findCreditorByCondition(map);
    }

    // 多条件查询债权的统计信息
    @Override
    public List<Object[]> findCreditorBySum(Map<String, Object> map) {
        return creditor4SqlDAO.findCreditorBySum(map);
    }

    // 债权的审核
    @Override
    public void checkCreditor(String[] id) {
        for (int i = 0; i < id.length; i++) {
            // 1.根据id去查询债权
            CreditorModel creditor = creditorDAO.findOne(Integer.parseInt(id[i].trim()));

            if (creditor != null) {
                //查找到
                //2.修改债权的状态
                creditor.setDebtStatus(ClaimsType.CHECKED);
            }
        }


    }
}
