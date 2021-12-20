package com.lcat.common.exception;

import com.lcat.common.lang.ErrorCode;
import lombok.Getter;

/**
 * Description
 * Date 2021/11/3
 * Created by muggle
 */
@Getter
public class BusinesException extends RuntimeException {
    private Integer code;

    public BusinesException(String message,Integer code) {
        super(message);
        this.code=code;
    }
    public BusinesException(Throwable e,Integer code){
        super(e);
        this.code=code;
    }

    public BusinesException(String message,Integer code,Throwable e){
        super(message,e);
        this.code=code;
    }
    public BusinesException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.code=errorCode.getCode();
    }
}
