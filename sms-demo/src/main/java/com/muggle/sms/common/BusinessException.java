package com.muggle.sms.common;

/**
 * Description
 * Date 2021/10/14
 * Created by muggle
 */
public class BusinessException extends RuntimeException {
    private Integer status;

    public BusinessException(Integer status, Throwable e){
        super(e);
        this.status=status;
    }

    public BusinessException(Integer status, String message){
        super(message);
        this.status=status;
    }

    public BusinessException(ErrorCode errorCode){
        super(errorCode.getErrorMessage());
        this.status=errorCode.getStatus();
    }

    public Integer getStatus() {
        return status;
    }
}
