package com.muggle.sms.test;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.weike.utils.Config;

public class Tests0 {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final int TYPE_NORMAL = 0;//短信类型，0 为普通短信，1 营销短信
	private static final int SUCCESS_CODE = 0;
	private static final String NATION_CODE = "86";

	public void test1() throws IOException {
		try {
			SmsSingleSender ssender = new SmsSingleSender(Config.SMS_PROVIDER_TENCENT_APPID, Config.SMS_PROVIDER_TENCENT_APPKEY);
			SmsSingleSenderResult result;
			result = ssender.send(TYPE_NORMAL, NATION_CODE, "15813036172", "腾鸟科技手机验证码为12345，请于5内完成验证", "", "");
			if (result.result == SUCCESS_CODE) {
				logger.info("result is {}", JsonUtil.toJsonString(result));
			} else {
				logger.error("result is {}", JsonUtil.toJsonString(result));
			}
		} catch (Exception e) {
			logger.error("短信发送异常,短信内容：", e);
		}

	}

}
