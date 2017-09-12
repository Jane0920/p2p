package com.xyr.service.impl;

import com.xyr.dao.AccountLogDAO;
import com.xyr.dao.FundingNotMatchedDAO;
import com.xyr.dao.ProductAccountDAO;
import com.xyr.dao.UserAccountDAO;
import com.xyr.domain.AccountLog;
import com.xyr.domain.FundingNotMatchedModel;
import com.xyr.domain.ProductAccount;
import com.xyr.domain.UserAccount;
import com.xyr.service.ProductAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xyr on 2017/9/12.
 */
@Service
public class ProductAccountServiceImpl implements ProductAccountService {

    @Autowired
    private ProductAccountDAO productAccountDAO;
    @Autowired
    private UserAccountDAO userAccountDAO;
    @Autowired
    private AccountLogDAO accountLogDAO;
    @Autowired
    private FundingNotMatchedDAO fundingNotMatchedDAO;

    @Override
    @Transactional
    public void addProductAccount(UserAccount userAccount, ProductAccount productAccount, AccountLog accountLog, FundingNotMatchedModel fundingNotMatchedModel) {
        userAccountDAO.updateNewInvestmentUserAccount(userAccount.getBalance(), userAccount.getInverstmentW(),
                userAccount.getInterestTotal(), userAccount.getRecyclingInterest(), userAccount.getInverstmentA(),
                userAccount.getId());

        productAccountDAO.save(productAccount);

        accountLog.setpId(productAccount.getpId());
        accountLogDAO.save(accountLog);

        fundingNotMatchedModel.setfInvestRecordId(productAccount.getpId());
        fundingNotMatchedDAO.save(fundingNotMatchedModel);
    }
}
