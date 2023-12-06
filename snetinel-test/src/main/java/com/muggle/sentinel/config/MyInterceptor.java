package com.muggle.sentinel.config;

import com.alibaba.csp.sentinel.context.ContextUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ContextUtil.enter("test.hello",  request.getHeader("x-forwarded-for"));
        ContextUtil.exit();
        return true;
    }
}
