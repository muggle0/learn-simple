package com.muggle.jprotobuf.server;

import com.baidu.jprotobuf.pbrpc.ProtobufRPCService;
import com.muggle.jprotobuf.clinet.JprotobufClient;
import com.muggle.jprotobuf.model.JprotobufRequst;
import com.muggle.jprotobuf.model.JprotobufResponse;

/**
 * Description
 * Date 2024/8/7
 * Created by muggle
 */
public class JprotobufServer implements JprotobufClient {
    @ProtobufRPCService(serviceName = "echoService", methodName = "echo")
    @Override
    public JprotobufResponse echo(JprotobufRequst requst) {
        return null;
    }
}
