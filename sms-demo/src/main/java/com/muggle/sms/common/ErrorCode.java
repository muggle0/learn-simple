package com.muggle.sms.common;

/**
 * Description
 * Date 2021/10/14
 * Created by muggle
 */

public enum ErrorCode {
    ;

    private String errorMessage;

    private Integer status;

    ErrorCode(String errorMessage, Integer status) {
        this.errorMessage = errorMessage;
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
