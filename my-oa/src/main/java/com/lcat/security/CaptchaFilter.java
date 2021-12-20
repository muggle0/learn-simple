package com.lcat.security;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lcat.common.exception.CaptchaException;
import com.lcat.common.lang.Const;
import com.lcat.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 校验验证码的过滤器
 */
@Component
public class CaptchaFilter extends OncePerRequestFilter {

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private LoginFailureHandler loginFailureHandler;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
		String url = httpServletRequest.getRequestURI();
		if ("/login".equals(url) && httpServletRequest.getMethod().equals("POST")) {
			try{
				// 校验验证码
				validate(httpServletRequest);
			} catch (CaptchaException e) {
				// 交给认证失败处理器
				loginFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
			}
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
		return;
	}

	// 校验验证码逻辑
	private void validate(HttpServletRequest httpServletRequest) {
		String code = httpServletRequest.getParameter("code");
		String key = httpServletRequest.getParameter("token");

		if (StringUtils.isBlank(code) || StringUtils.isBlank(key)) {
			throw new CaptchaException("请填写验证码");
		}
		final Object hget = redisUtil.hget(Const.CAPTCHA_KEY, key);
		if (hget==null){
			throw new CaptchaException("验证码过期");
		}
		if (!code.equals(hget)) {
			throw new CaptchaException("验证码错误");
		}
		// 一次性使用
		redisUtil.hdel(Const.CAPTCHA_KEY, key);
	}
}
