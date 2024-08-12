package com.muggle.jprotobuf.model;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

/**
 * Description
 * Date 2024/8/7
 * Created by muggle
 */
public class EchoInfo {
    @Protobuf
    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
