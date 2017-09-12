package com.xyr.controller;

import com.xyr.cache.BaseCacheService;
import com.xyr.dao.ProductDAO;
import com.xyr.domain.AccountLog;
import com.xyr.domain.BankCardInfo;
import com.xyr.domain.ExpectedReturn;
import com.xyr.domain.FundingNotMatchedModel;
import com.xyr.domain.Product;
import com.xyr.domain.ProductAccount;
import com.xyr.domain.User;
import com.xyr.domain.UserAccount;
import com.xyr.domain.WeigthRule;
import com.xyr.service.BankCardInfoService;
import com.xyr.service.ChargesService;
import com.xyr.service.ExpectedReturnService;
import com.xyr.service.ProductAccountService;
import com.xyr.service.ProductService;
import com.xyr.service.UserAccountService;
import com.xyr.service.WeigthRuleService;
import com.xyr.utils.BigDecimalUtil;
import com.xyr.utils.FundsFlowType;
import com.xyr.utils.InvestStatus;
import com.xyr.utils.InvestTradeType;
import com.xyr.utils.RandomNumberUtil;
import com.xyr.utils.ResponseCode;
import com.xyr.utils.ServerResponse;
import com.xyr.utils.TimestampUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by xyr on 2017/9/11.
 */
@Controller
@RequestMapping("/charges")
public class ChargesController {

    @Autowired
    private BaseCacheService baseCacheService;
    @Autowired
    private ChargesService chargesService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private BankCardInfoService bankCardInfoService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WeigthRuleService weigthRuleService;
    @Autowired
    private ProductAccountService productAccountService;
    @Autowired
    private ExpectedReturnService expectedReturnService;

    /**
     * 用户充值
     *
     * @param token
     * @param chargeMoney
     * @return
     */
    @RequestMapping("/charge")
    @ResponseBody
    public ServerResponse charge(@RequestHeader(value = "token") String token, Double chargeMoney) {
        if (StringUtils.isEmpty(token))
            return ServerResponse.createByError(ResponseCode.NULL_TOKEN.getCode());

        Map<String, Object> userMap = baseCacheService.getHmap(token);
        if (userMap == null || userMap.size() == 0)
            return ServerResponse.createByError(ResponseCode.LOGIN_INVALID.getCode());

        int userId = (int) userMap.get("id");
        BankCardInfo bankCardInfo = bankCardInfoService.findByUserId(userId);
        if (bankCardInfo == null)
            return ServerResponse.createByError();
        chargesService.reCharge(chargeMoney, bankCardInfo.getBankCardNum());

        return userAccountService.updateBalanceAndTotal(chargeMoney, userId);
    }

    /**
     * 购买理财产品
     * @param token
     * @param pProductId
     * @param pAmount
     * @param pDeadline
     * @param pExpectedAnnualIncome
     * @param pMonthInterest
     * @param pMonthlyExtractInterest
     * @return
     */
    @RequestMapping("/addMayTake")
    @ResponseBody
    public ServerResponse addMayTake(@RequestHeader(value = "token") String token, long pProductId, String pAmount, String pDeadline,
                                     String pExpectedAnnualIncome, String pMonthInterest, String pMonthlyExtractInterest
    ) {
        if (StringUtils.isEmpty(token))
            return ServerResponse.createByError(ResponseCode.NULL_TOKEN.getCode());

        Map<String, Object> userMap = baseCacheService.getHmap(token);
        //判断用户是否登录
        if (userMap == null || userMap.size() == 0)
            return ServerResponse.createByError(ResponseCode.LOGIN_INVALID.getCode());

        //得到到期收回的总本息
        String endInvestTotalMoney = BigDecimalUtil.endInvestTotalMoney(pAmount, pDeadline, pExpectedAnnualIncome, pMonthlyExtractInterest);
        //月取计划的待收益利息 收回的总本息-投资金额
        BigDecimal mayInterrestIncome = BigDecimalUtil.sub(endInvestTotalMoney, pAmount);

        int userId = (int) userMap.get("id");
        String username = (String) userMap.get("username");
        // 3.封装用户帐户表信息
        // 先查询用户帐户表数据
        UserAccount userAccount = userAccountService.findByUserId(userId);
        // a. 修改帐户中的余额 现余额=原余额-投资金额
        BigDecimal _balance = BigDecimalUtil.sub(userAccount.getBalance(), Double.parseDouble(pAmount));
        // b. 总计待收本金 =原总计待收本金+投资金额
        BigDecimal _inverstmentW = BigDecimalUtil.add(userAccount.getInverstmentW(), Double.parseDouble(pAmount));
        // c. 总计待收利息 =原总计待收利息+本次待收利息
        BigDecimal _interestTotal = BigDecimalUtil.add(userAccount.getInterestTotal(),
                mayInterrestIncome.doubleValue());
        // d. 月取计划投资总额 原月取总额+投资金额
        BigDecimal _recyclingInterest = BigDecimalUtil.add(userAccount.getRecyclingInterest(),
                Double.parseDouble(pAmount));
        // e. 已投资总额 原投资总额+投资金额
        BigDecimal _inverstmentA = BigDecimalUtil.add(userAccount.getInverstmentA(), Double.parseDouble(pAmount));

        UserAccount uam = new UserAccount();
        uam.setBalance(_balance.doubleValue());
        uam.setInverstmentW(_inverstmentW.doubleValue());
        uam.setInterestTotal(_interestTotal.doubleValue());
        uam.setRecyclingInterest(_recyclingInterest.doubleValue());
        uam.setInverstmentA(_inverstmentA.doubleValue());
        uam.setId(userAccount.getId());

        //处理投资信息 ProductAccount
        // 4.ProductAccount表数据封装
        ProductAccount pa = new ProductAccount();

        // 它需要产品信息 ---从请求参数中可以获取产品id,从数据库中查询出产品信息
        Product product = (Product) productService.findProductById(pProductId).getData();
        pa.setpProductName(product.getProductName());
        pa.setpProductId(product.getProId());
        // 它需要用户信息--token
        pa.setpUid((long) userId);
        pa.setUsername(username);
        // 本身信息
        // a. 开始时间 ---new Date()
        Date date = new Date();
        pa.setpBeginDate(date);
        // b. 结束时间 ---new Date()+投资期限
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, Integer.parseInt(pDeadline));
        pa.setpEndDate(c.getTime());
        // c. 投资编号—“TZNO”+随机id它是根据时间生成
        String randomNum = RandomNumberUtil.randomNumber(date);
        pa.setpSerialNo("TZNO" + randomNum);
        // d. 投资金额----请求参数
        pa.setpAmount(Double.parseDouble(pAmount));
        // e. 投资期限---请求参数
        pa.setpDeadline(Integer.parseInt(pDeadline));
        // f. 年化率-----请求参数
        pa.setpExpectedAnnualIncome(Double.parseDouble(pExpectedAnnualIncome));
        // g. 月利息-----请求参数
        pa.setpMonthInterest(Double.parseDouble(pMonthInterest));
        // h. 月提取利息----请求参数
        pa.setpMonthlyExtractInterest(Double.parseDouble(pMonthlyExtractInterest));
        // i. 可用余额-----在用户帐户表中有
        pa.setpAvailableBalance(_balance.doubleValue());
        // j. 到期收回总本金----在用户帐户表中有
        pa.setpEndInvestTotalMoney(_inverstmentW.doubleValue());
        // k. 待匹配状态---从匹配状态工具类中获取 InvestStatus
        pa.setpStatus(InvestStatus.WAIT_TO_MATCH);
        // l. 还有其它项
        pa.setaCurrentPeriod(1);

        // 5.交易流水
        AccountLog accountLog = new AccountLog();
        // 1.需要用户id
        accountLog.setaUserId(userId);
        // 2.主帐户id记录成用户id
        accountLog.setaMainAccountId(userId);
        // 3.需要投资记录的id----就是ProductAccount主键
        // 4.当前期----第一期
        accountLog.setaCurrentPeriod(1);
        // 5.收付-----从工具类中获取InvestTradeType. PAY
        accountLog.setaReceiveOrPay(InvestTradeType.PAY);
        // 6.交易流水号 LSNO+随机id
        accountLog.setaTransferSerialNo("LSNO" + randomNum);
        // 7.交易时间 new Date()
        accountLog.setaDate(date);
        // 8.交易类型 FundsFlowType. INVEST_TYPE
        accountLog.setaType(FundsFlowType.INVEST_TYPE);
        // 9.交易状态 FundsFlowType. INVEST_SUCCESS
        accountLog.setaTransferStatus(FundsFlowType.INVEST_SUCCESS);
        // 10.交易前金额 记录的是交易前的余额
        accountLog.setaBeforeTradingMoney(userAccount.getBalance());
        ;
        // 11.交易金额 投资的金额
        accountLog.setaAmount(Double.parseDouble(pAmount));
        // 12.交易后金额 记录的是交易后的余额
        accountLog.setaAfterTradingMoney(_balance.doubleValue());
        // 13.交易详情 月取操作+投资流水号
        accountLog.setaDescreption("月取计划TZNO" + randomNum);

        // 6.向待匹配资金表中插入数据
        FundingNotMatchedModel fnmm = new FundingNotMatchedModel();
        // 1.投资记录id---就是ProductAccount的id
        // 2.待匹配金额----就是投资金额
        fnmm.setfNotMatchedMoney(Double.parseDouble(pAmount));
        // 3.资金类型 124 新增投资
        fnmm.setfFoundingType(124);
        // 4.投资时间 new Date
        fnmm.setfCreateDate(date);
        // 5.权重
        WeigthRule wr = weigthRuleService.findByWeigthType(124);
        fnmm.setfFoundingWeight(wr.getWeigthValue());
        fnmm.setfIsLocked(FundsFlowType.FUND_NOT_LOCK);
        fnmm.setUserId(userId);

        // 7.操作
        productAccountService.addProductAccount(uam, pa, accountLog, fnmm);

        // 8.预期收益操作
        for (int i = 0; i < Integer.parseInt(pDeadline); i++) {
            ExpectedReturn er = new ExpectedReturn();
            // 封装数据
            // 1. 用户id
            er.setUserId(userId);
            // 2. 产品id
            er.setProductId((int) (product.getProId()));
            // 3. 投资记录id
            er.setInvestRcord(pa.getpId());
            // 4. 收益日期 当前月份+1
            er.setExpectedDate(TimestampUtils.nextMonth(date.getYear(), date.getMonth(), i));
            // 5. 收益金额、-----从请求参数中获取
            er.setExpectedMoney(Double.parseDouble(pMonthInterest));
            // 6. 创建日期 new Date()
            er.setCreateDate(date);
            expectedReturnService.add(er);
        }

        return ServerResponse.createBySuccess();

    }

}
