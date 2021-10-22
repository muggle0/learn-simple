package com.muggle.sms.proxy;

import com.muggle.sms.common.SmsMessageResp;

/**
 * Description
 * Date 2021/10/14
 * Created by muggle
 */
public interface SmsLoadBalance {

    /**
     * 如运行过程中默认的短信通道出现异常且失败重发也不行则切换到其他短信通道进行短信发送。
     * <br/></>统一消息平台在接收到业务应用接口调用后，进行消息收集，同时监听区短信通道是否正常，若异常则消息队列自动切换到市短信平台接口调用，并优先进行异常预警。区短信通道监听正常时复位。
     * @return
     */
    SmsMessageResp sendMessageLoadBalance();

}
