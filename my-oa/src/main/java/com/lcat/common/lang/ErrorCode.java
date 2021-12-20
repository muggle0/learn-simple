package com.lcat.common.lang;

import lombok.Getter;

@Getter
public enum  ErrorCode {
    USER_ERR0R("用户类型异常",6001),
    UNKNOW_ERROR("系统异常",5001);

    private String message;
    private int code;

    ErrorCode(String message, int code) {
        this.message = message;
        this.code = code;
    }

}
