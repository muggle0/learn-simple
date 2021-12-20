package com.lcat.controller;

import cn.hutool.core.lang.UUID;
import com.google.code.kaptcha.Producer;
import com.lcat.common.lang.Const;
import com.lcat.common.lang.ResultBean;
import com.lcat.entity.SysUser;
import com.lcat.entity.vo.SysUserVO;
import com.lcat.service.impl.SysUserServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController extends BaseController{

	@Autowired
	private Producer producer;
	@Autowired
    private SysUserServiceImpl sysUserServiceImpl;

	/**
	 * 获取验证码,通过key存入redis
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/api/captcha")
	public ResultBean<Map<String,String>> captcha() throws IOException {
		String key = UUID.randomUUID().toString();
		String code = producer.createText();
		BufferedImage image = producer.createImage(code);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", outputStream);
		BASE64Encoder encoder = new BASE64Encoder();
		String str = "data:image/jpeg;base64,";
		String base64Img = str + encoder.encode(outputStream.toByteArray());
		redisUtil.hset(Const.CAPTCHA_KEY, key, code, 120);
		Map<String, String> data=new HashMap<>();
		data.put("captchaImg",base64Img);
		data.put("token",key);
		return ResultBean.successData(data);
	}

	/**
	 * 获取用户信息接口
	 * @param principal
	 * @return
	 */
	@GetMapping("/sys/userInfo")
	public ResultBean<SysUserVO> userInfo(Principal principal) {
        SysUser userInfo = sysUserServiceImpl.getUserInfo(principal.getName());
        SysUserVO sysUserVO = new SysUserVO();
        BeanUtils.copyProperties(userInfo, sysUserVO);
        return ResultBean.successData(sysUserVO);
	}
}
