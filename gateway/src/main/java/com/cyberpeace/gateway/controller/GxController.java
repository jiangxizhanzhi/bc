package com.cyberpeace.gateway.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.gateway.controller.req.GxAccessReq;
import com.cyberpeace.integratation.model.mp.GxToken;
import com.cyberpeace.integratation.service.RemoteUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "共性平台接入相关api")
@Controller
@RequestMapping("/auth/")
public class GxController {

	@Autowired
	private RemoteUserService remoteUserService;

	@ApiOperation("单点登录跳转")
	@PostMapping("/access")
	public void access(
			@ApiParam(name = "token", value = "token") @RequestParam(value = "token", required = false) String token,
			@RequestBody @Validated GxAccessReq gxAccessReq, HttpServletResponse response)
			throws Exception {

		// 提取远程用户token
		String gxToken;
		if (token != null) {
			gxToken = token;
		} else {
			gxToken = gxAccessReq.getToken();
		}

		setResponse(response, gxToken);

	}

	@ApiOperation("单点登录跳转")
	@GetMapping("/access")
	public void access(
			@ApiParam(name = "token", value = "token") @RequestParam("token") String token,
			HttpServletResponse response) throws Exception {

		setResponse(response, token);

	}

	/** 设置重定向返回体 */
	private void setResponse(HttpServletResponse response, String gxToken) {

		// 校验token是否为空
		if (gxToken == null) {
			throw new BusinessException(ErrorCode.USER_INFO_MISSING);
		}

		// 转换
		GxToken tokenContext = new GxToken(gxToken);
		String mpToken = remoteUserService.getMpUserToken(tokenContext);

		// 构造返回体
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader("Location", "/page/mg/");
		Cookie cookie = new Cookie("cr_jwttoken", mpToken);
		response.addCookie(cookie);

	}

}
