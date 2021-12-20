package com.lcat.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcat.service.*;
import com.lcat.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	protected RedisUtil redisUtil;

	/**
	 * 获取页面
	 * @return
	 */
	public Page getPage() {
		int current = ServletRequestUtils.getIntParameter(request, "cuurent", 1);
		int size = ServletRequestUtils.getIntParameter(request, "size", 10);
		return new Page(current, size);
	}

}
