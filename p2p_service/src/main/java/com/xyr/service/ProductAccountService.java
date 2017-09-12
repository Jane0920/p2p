package com.xyr.service;

import com.xyr.domain.AccountLog;
import com.xyr.domain.FundingNotMatchedModel;
import com.xyr.domain.ProductAccount;
import com.xyr.domain.UserAccount;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xyr on 2017/9/12.
 */
public interface ProductAccountService {

    @Transactional
    void addProductAccount(UserAccount userAccount, ProductAccount productAccount, AccountLog accountLog, FundingNotMatchedModel fundingNotMatchedModel);

}
