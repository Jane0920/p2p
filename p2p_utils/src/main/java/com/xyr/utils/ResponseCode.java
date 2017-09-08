package com.xyr.utils;

/**
 * Created by xyr on 2017/8/28.
 */
public enum ResponseCode {

    BREAK_DOWN(0, "失败"),
    SUCCESS(1, "成功"),
    NULL_RESULT(2, "没有结果"),
    NULL_TOKEN(13, "token为空"),
    LOGIN_INVALID(15, "用户未登录"),
    CAPITAL_TOO_LARGE(17, "投资金额不能大于账户余额"),
    NULL_OF_VALIDATE_CODE(26, "验证码为空"),
    INPUT_ERROR_OF_VALIDATE_CODE(27, "验证码输入不正确"),
    ERROR_OF_USERNAME_PASSWORD(66, "用户名密码错误"),
    PHONE_ALREADY_REGISTER(67, "手机已经被注册"),
    ALREADY_EXIST_OF_USERNAME(70, "用户名已经存在"),
    ADD_FALIUE(81, "添加失败");

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
