package com.muggle.sms.server;

import com.muggle.sms.common.SmsMessage;
import com.muggle.sms.common.SmsMessageResp;

/**
 * Description
 * Date 2021/10/14
 * Created by muggle
 */
public interface SmsServer {

    SmsMessageResp sendMessage(SmsMessage message);

    boolean messageChannlTest();
}
