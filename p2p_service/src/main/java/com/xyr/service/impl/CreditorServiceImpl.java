package com.xyr.service.impl;

import com.xyr.dao.CreditorDAO;
import com.xyr.domain.CreditorModel;
import com.xyr.service.CreditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyr on 2017/9/13.
 */
@Service
public class CreditorServiceImpl implements CreditorService {

    @Autowired
    private CreditorDAO creditorDAO;

    @Override
    public void addCreditor(CreditorModel creditorModel) {
        creditorDAO.saveAndFlush(creditorModel);
    }

    @Override
    public void addCreditor(List<CreditorModel> creditorModels) {
        creditorDAO.save(creditorModels);
    }
}
