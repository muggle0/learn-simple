package com.lcat.security;

import cn.hutool.json.JSONUtil;
import com.lcat.common.lang.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 */
@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
		log.error("认证失败处理器",accessDeniedException);
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		ServletOutputStream outputStream = response.getOutputStream();
		ResultBean result = ResultBean.error(accessDeniedException.getMessage(),HttpServletResponse.SC_FORBIDDEN);
		outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));
		outputStream.flush();
		outputStream.close();
	}
}
