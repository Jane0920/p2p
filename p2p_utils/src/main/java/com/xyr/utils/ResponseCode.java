package com.xyr.utils;

/**
 * Created by xyr on 2017/8/28.
 */
public enum ResponseCode {

    BREAK_DOWN(0, "失败"),
    SUCCESS(1, "成功"),
    NULL_RESULT(2, "没有结果"),
    CARDINFO_ALEARD_EXIST(5, "已经添加过银行卡信息"),
    NO_USER(7, "没有该用户"),
    NULL_TOKEN(13, "token为空"),
    LOGIN_INVALID(15, "用户未登录"),
    CAPITAL_TOO_LARGE(17, "投资金额不能大于账户余额"),
    NULL_OF_VALIDATE_CODE(26, "验证码为空"),
    INPUT_ERROR_OF_VALIDATE_CODE(27, "验证码输入不正确"),
    CONTRACT_NO_IS_NULL(38, "借款Id（合同编号）不能为空"),
    DEBTORS_NAME_IS_EMPTY(39, "债务人不能为空"),
    DEBTORS_ID_IS_EMPTY(40, "身份证号不能为空"),
    LOAN_PURPOSE_IS_EMPTY(41, "借款用途不能为空"),
    LOAN_TYPE_IS_EMPTY(42, "借款类型不能为空"),
    LOAN_PERIOD_IS_EMPTY(43, "原始期限（月）不能为空"),
    LOAN_START_DATE_IS_EMPTY(44, "原始借款开始日期不能为空"),
    LOAN_END_DATE_IS_EMPTY(45, "原始借款结束日期不能为空"),
    REPAYMENT_STYLE_IS_EMPTY(46, "还款方式不能为空"),
    REPAYMENT_DATE_IS_EMPTY(47, "还款日期不能为空"),
    REPAYMENT_MONEY_IS_EMPTY(48, "还款金额不能为空"),
    DEBT_MONEY_IS_EMPTY(49, "债权金额不能为空"),
    DEBT_MONTH_RATE_IS_EMPTY(50, "债权年化利率（%）不能为空"),
    DEBT_TRANSFERRED_MONEY_IS_EMPTY(51, "债权转入金额不能为空"),
    DEBT_TRANSFERRED_DATE_IS_EMPTY(52, "债权转入日期不能为空"),
    DEBT_TRANSFERRED_PERIOD_IS_EMPTY(53, "债权转入期限（月）不能为空"),
    DEBT_RANSFER_OUT_DATE_IS_EMPTY(54, "债权转出日期不能为空"),
    CREDITOR_IS_EMPTY(55, "债权人不能为空"),
    ERROR_OF_USERNAME_PASSWORD(66, "用户名密码错误"),
    PHONE_ALREADY_REGISTER(67, "手机已经被注册"),
    ALREADY_EXIST_OF_USERNAME(70, "用户名已经存在"),
    ADD_FALIUE(81, "添加失败"),
    IMPORT_FALIED(101, "导入数据失败，xx条出现错误");

    private final int code;
    private final String value;

    ResponseCode(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
