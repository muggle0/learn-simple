package com.muggle.jprotobuf.clinet;

import com.baidu.jprotobuf.pbrpc.ProtobufRPC;
import com.muggle.jprotobuf.model.JprotobufRequst;
import com.muggle.jprotobuf.model.JprotobufResponse;

/**
 * Description
 * Date 2024/8/7
 * Created by muggle
 */
public interface JprotobufClient {
    /**
     * To define a RPC client method. <br>
     * serviceName is "echoService"
     * methodName is use default method name "echo"
     * onceTalkTimeout is 200 milliseconds
     *
     * @return
     */
    @ProtobufRPC(serviceName = "echoService", onceTalkTimeout = 200)
    JprotobufResponse echo(JprotobufRequst requst);
}
