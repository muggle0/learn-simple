package com.lcat.security;

import cn.hutool.json.JSONUtil;
import com.lcat.common.lang.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登陆处理器
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		response.setContentType("application/json;charset=UTF-8");
		log.error("校验异常",authException);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		ServletOutputStream outputStream = response.getOutputStream();
		ResultBean result = ResultBean.error("没有权限",401);
		outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));
		outputStream.flush();
		outputStream.close();
	}
}
