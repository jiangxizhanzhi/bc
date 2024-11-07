package com.cyberpeace.cr.crbase.controller;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.integratation.service.RemoteUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 无需用户认证的接口
 */
@Api(tags = "靶场无需用户认证的接口")
@RestController
@Slf4j
@RequestMapping("/cr/noAuth")
public class CrNoAuthController {

	@Autowired
	TransactionTemplate transactionTemplate;


	@Autowired
	private RemoteUserService remoteUserService;

	@ApiOperation("辅助鉴权")
	@GetMapping("/auth")
	public BaseRsp auth(@ApiParam(name = "token", value = "jwt token") String token) {
		try {
			remoteUserService.doAuthentication(token);
		} catch (Exception e) {
			log.error("authentication failed, token={}", token, e);
			throw new AuthenticationException();
		}
		return new BaseRsp();
	}
}
