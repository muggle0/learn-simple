package com.muggle.webflux.test;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;

import java.io.Serializable;

/**
 * Description
 * Date 2024/8/5
 * Created by muggle
 */
@ProtobufClass
public class ProtobufModel implements Serializable {

    @Protobuf(fieldType = FieldType.STRING, order = 1, required = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
