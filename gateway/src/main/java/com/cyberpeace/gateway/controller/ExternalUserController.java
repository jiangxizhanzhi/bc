package com.cyberpeace.gateway.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.auth.service.SysUserService;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.IdListReq;
import com.cyberpeace.common.model.user.UserInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "外部接口-用户相关api")
@Slf4j
@RestController
@RequestMapping("/gateway/auth")
public class ExternalUserController {

	@Autowired
	private SysUserService sysUserService;

	@ApiOperation("批量查询用户")
	@PostMapping("/fetch/list")
	public BaseRsp<List<UserInfo>> getUserListByIds(@RequestBody IdListReq req) {
		Map<String, UserInfo> userInfoMap = sysUserService.getUserInfoMap(req.getIds());
		List<UserInfo> res = req.getIds().stream().filter(userInfoMap::containsKey)
				.map(userInfoMap::get).collect(Collectors.toList());
        return new BaseRsp<>(res);
	}
}
