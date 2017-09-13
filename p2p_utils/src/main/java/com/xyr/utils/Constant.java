package com.xyr.utils;


public class Constant {

    public static final String KEY = "cn.itcast.apis";
    public static final String BASE_64KEY = "Y24uaXRjYXN0LmFwaXM=";
    /**
     * 未选中退出队列
     */
    public static final String NULL_SELECT_OUTACCOUNT = "157";
    /**
     * 资金已结算成功
     */
    public static final String SETTLE_MENT_SUCCESS = "159";
    /**
     * 资金结算进行中
     */
    public static final String SETTLE_MENT_INIT = "160";
    /**
     * 债权已结算成功或债权结算进行中
     */
    public static final String DEBT_SETTLE_MENT_EXEC = "161";

    /**
     * 债权状态必须为已审核或正常还款
     */
    public static final String DEBT_CHECK_ADVANCESETTLE_STATUS = "162";
    /**
     * 资金结算失败
     */
    public static final String SETTLE_MENT_FAIL = "168";
}
