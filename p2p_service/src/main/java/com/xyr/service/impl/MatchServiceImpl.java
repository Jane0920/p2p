package com.xyr.service.impl;

import com.xyr.dao.CreditorDAO;
import com.xyr.dao.FundingNotMatchedDAO;
import com.xyr.domain.CreditorModel;
import com.xyr.domain.FundingNotMatchedModel;
import com.xyr.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyr on 2017/9/18.
 */
@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private FundingNotMatchedDAO fundingNotMatchedDAO;
    @Autowired
    private CreditorDAO creditorDAO;


    @Override
    public void startMatch() {
        // 1.获取所有待匹配资金 状态 10901 待匹配
        List<FundingNotMatchedModel> fnmm = fundingNotMatchedDAO.findByMatch();
        // 2.获取所有待匹配债权 已审核 11302 11401未匹配 11403 部分匹配
        List<CreditorModel> cm = creditorDAO.findAllMatch();

        match(fnmm, cm);
    }

    private void match(List<FundingNotMatchedModel> fnmm, List<CreditorModel> cm) {

    }
}
