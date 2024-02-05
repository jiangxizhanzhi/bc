package com.cyberpeace.gateway.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.gateway.model.PathContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "用户权限接入相关api")
@Controller
@RestController
@RequestMapping("/auth/")
public class AuthController {

	private String WJ_IP;

	private String WJ_WEB_PORT;

	private String WJ_WEB_PATH;

	@PostConstruct
	public void init() {

		// 尝试获取挖掘平台的IP
		WJ_IP = SystemPropUtil.getProp("WJ_IP");
		if (StringUtils.isBlank(WJ_IP)) {
			log.error("WJ_IP is null");
			WJ_IP = "127.0.0.1";
		}

		WJ_WEB_PORT = ConfigUtil.getContextProperty("vuln.platform.web.port");

		WJ_WEB_PATH = ConfigUtil.getContextProperty("vuln.platform.web.path");

	}

	@ApiOperation("单点登录-漏洞挖掘平台跳转")
	@GetMapping("/jump/vuln/")
	public void
			access(@ApiParam(name = "token", value = "token",
					required = false) @RequestParam(value = "token", required = false) String token,
					HttpServletResponse response) throws Exception {
		setResponse(response, token);
	}

	@ApiOperation("单点登录-获取漏洞挖掘平台跳转地址")
	@GetMapping("/jump/vuln/path/")
	public BaseRsp<PathContext> getVulnPath() {

		PathContext pathContext = new PathContext();
		String path = String.format("http://%s:%s%s", WJ_IP, WJ_WEB_PORT, WJ_WEB_PATH);
		pathContext.setPath(path);
		return new BaseRsp<>(pathContext);

	}

	/**
	 * 设置重定向返回体
	 */
	private void setResponse(HttpServletResponse response, String token) throws IOException {

		token = getToken(token);

		// 构造返回体
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader("Cache-Control", "no-cache");

		// 设置跳转路径
		String targetUrl = String.format("http://%s:%s%s?token=%s", WJ_IP, WJ_WEB_PORT, WJ_WEB_PATH,
				token);
		response.setHeader("Location", targetUrl);

	}

	private String getToken(String token) {

		// 优先使用当前用户token
		String currentToken = CommonContext.getAuthorization();
		token = currentToken == null ? token : currentToken;

		// 校验token是否为空
		if (token == null) {
			throw new BusinessException(ErrorCode.USER_INFO_MISSING);
		}

		return token;
	}

}
