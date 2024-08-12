package com.muggle.jprotobuf.server;

import com.baidu.jprotobuf.pbrpc.ProtobufRPCService;
import com.muggle.jprotobuf.model.EchoInfo;

/**
 * Description
 * Date 2024/8/7
 * Created by muggle
 */
public class EchoServiceImpl {
    @ProtobufRPCService(serviceName = "echoService", methodName = "echo")
    public EchoInfo doEcho(EchoInfo info) {
        EchoInfo ret = new EchoInfo();
        ret.setMessage("hello:" + info.message);

        return ret;
    }
}
