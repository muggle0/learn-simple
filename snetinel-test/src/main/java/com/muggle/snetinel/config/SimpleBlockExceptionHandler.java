package com.muggle.snetinel.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Description
 * Date 2021/5/5
 * Created by muggle
 */
public class SimpleBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        // Return 429 (Too Many Requests) by default.
//        httpServletResponse.setStatus(429);
        httpServletResponse.setContentType("application/json");
        PrintWriter out = httpServletResponse.getWriter();
        out.print("{\"code\":500}");
        out.flush();
        out.close();
    }
}
