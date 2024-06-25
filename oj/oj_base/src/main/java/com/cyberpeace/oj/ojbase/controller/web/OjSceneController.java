package com.cyberpeace.oj.ojbase.controller.web;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.bo.AccessConnectionReq;
import com.cyberpeace.integratation.service.bo.AccessConnectionRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "oj-场景相关api")
@RestController
@RequestMapping("/oj/scene/")
@RequiresAuthentication
public class OjSceneController {

	@Autowired
	private RemoteSceneService remoteSceneService;

	@ApiOperation("场景实例获取远程连接")
	@PostMapping("/get_connection")
	public BaseRsp<AccessConnectionRsp> getAccessConnection(@RequestBody AccessConnectionReq req) {
		AccessConnectionRsp rsp = remoteSceneService.getConnection(req);
		return new BaseRsp<>(rsp);
	}
}
