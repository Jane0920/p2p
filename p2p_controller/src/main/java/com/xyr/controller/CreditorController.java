package com.xyr.controller;

import com.xyr.domain.CreditorModel;
import com.xyr.service.CreditorService;
import com.xyr.utils.ClaimsType;
import com.xyr.utils.Constant;
import com.xyr.utils.RandomNumberUtil;
import com.xyr.utils.ResponseCode;
import com.xyr.utils.ServerResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xyr on 2017/9/13.
 */
@Controller
@RequestMapping("/creditor")
public class CreditorController {

    @Autowired
    private CreditorService creditorService;

    /**
     * 单笔债权录入
     *
     * @param creditor
     * @return
     */
    @RequestMapping("/addCreditor")
    @ResponseBody
    public ServerResponse addCreditor(CreditorModel creditor) {
        if (StringUtils.isEmpty(creditor.getContractNo())) { // 判断借款Id（合同编号）是否为空
            return ServerResponse.createByError(ResponseCode.CONTRACT_NO_IS_NULL.getCode());
        }
        if (StringUtils.isEmpty(creditor.getDebtorsName())) { // 判断债务人是否为空
            return ServerResponse.createByError(ResponseCode.DEBTORS_NAME_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getDebtorsId())) { // 判断身份证号是否为空
            return ServerResponse.createByError(ResponseCode.DEBTORS_ID_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getLoanPurpose())) { // 判断借款用途是否为空
            return ServerResponse.createByError(ResponseCode.LOAN_PURPOSE_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getLoanType())) { // 判断借款类型是否为空
            return ServerResponse.createByError(ResponseCode.LOAN_TYPE_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getLoanPeriod() + "")) { // 判断原始期限（月）是否为空
            return ServerResponse.createByError(ResponseCode.LOAN_PERIOD_IS_EMPTY.getCode());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        if (StringUtils.isEmpty(sdf.format(creditor.getLoanStartDate()))) { // 判断原始借款开始日期是否为空
            return ServerResponse.createByError(ResponseCode.LOAN_START_DATE_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(sdf.format(creditor.getLoanEndDate()))) { // 判断原始借款到期日期是否为空
            return ServerResponse.createByError(ResponseCode.LOAN_END_DATE_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getRepaymentStyle() + "")) { // 判断还款方式是否为空
            return ServerResponse.createByError(ResponseCode.REPAYMENT_STYLE_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getRepaymenDate())) { // 判断还款日是否为空
            return ServerResponse.createByError(ResponseCode.REPAYMENT_DATE_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getRepaymenMoney() + "")) { // 判断还款金额（元）是否为空
            return ServerResponse.createByError(ResponseCode.REPAYMENT_MONEY_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getDebtMoney() + "")) { // 判断债权金额（元）是否为空
            return ServerResponse.createByError(ResponseCode.DEBT_MONEY_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getDebtMonthRate() + "")) { // 判断债权年化利率（%）是否为空
            return ServerResponse.createByError(ResponseCode.DEBT_MONTH_RATE_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getDebtTransferredMoney() + "")) { // 判断债权转入金额是否为空
            return ServerResponse.createByError(ResponseCode.DEBT_TRANSFERRED_MONEY_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(sdf.format(creditor.getDebtTransferredDate()))) { // 判断债权转入日期是否为空
            return ServerResponse.createByError(ResponseCode.DEBT_TRANSFERRED_DATE_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getDebtTransferredPeriod() + "")) { // 判断债权转入期限（月）是否为空
            return ServerResponse.createByError(ResponseCode.DEBT_TRANSFERRED_PERIOD_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(sdf.format(creditor.getDebtRansferOutDate()))) { // 判断债权转出日期是否为空
            return ServerResponse.createByError(ResponseCode.DEBT_RANSFER_OUT_DATE_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getCreditor())) { // 判断债权人是否为空
            return ServerResponse.createByError(ResponseCode.CREDITOR_IS_EMPTY.getCode());
        }
        //录入债权信息
        creditor.setDebtNo("ZQNO" + RandomNumberUtil.randomNumber(new Date()));//债权id
        creditor.setBorrowerId(1);//贷款人id
        creditor.setDebtStatus(ClaimsType.UNCHECKDE); //债权状态
        creditor.setMatchedMoney(0.00);//匹配金额
        creditor.setMatchedStatus(ClaimsType.UNMATCH); //匹配状态
        creditor.setDebtType(Constant.NULL_SELECT_OUTACCOUNT);//标的类型
        creditor.setAvailablePeriod(creditor.getDebtTransferredPeriod());//可用期限
        creditor.setAvailableMoney(creditor.getDebtTransferredMoney());//可用金额
        creditorService.addCreditor(creditor);
        return ServerResponse.createBySuccess();
    }

}
