package com.muggle.jprotobuf.model;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import lombok.Data;

/**
 * Description
 * Date 2024/8/7
 * Created by muggle
 */
@Data
public class JprotobufRequst {

    @Protobuf
    public String message;
}
