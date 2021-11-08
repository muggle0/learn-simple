package com.muggle.sms.proxy;

import com.muggle.sms.common.SmsMessage;
import com.muggle.sms.common.SmsMessageResp;
import com.muggle.sms.server.SmsServer;

/**
 * Description
 * Date 2021/10/14
 * Created by muggle
 */
public interface SmsProxy {

    void setLoadBalanc(SmsLoadBalance smsLoadBalance);

    void addSmsServer(SmsServer smsServer);

    void removeSmsServer(SmsServer smsServer);


    default SmsMessageResp sendMessage(SmsMessage message){
        return null;
    }
}
