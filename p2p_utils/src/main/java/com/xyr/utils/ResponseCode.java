package com.xyr.utils;

/**
 * Created by xyr on 2017/8/28.
 */
public enum ResponseCode {

    BREAK_DOWN(0, "失败"),
    SUCCESS(1, "成功"),
    NULL_RESULT(2, "没有结果");

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
