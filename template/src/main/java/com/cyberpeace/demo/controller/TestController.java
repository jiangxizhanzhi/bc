package com.cyberpeace.demo.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteTopoService;

import io.swagger.annotations.Api;

@Api(tags = "测试接口")
@RestController
@RequestMapping("/test")
@RequiresAuthentication
public class TestController {

	@Autowired
	private RemoteTopoService remoteTopoService;

	String testTopoId = "1dd035784f5b44869c4e96aa07c31fd5";

	@PostMapping("/copytopo")
	public BaseRsp createTopo() {
		String result = remoteTopoService.copyTopo(testTopoId);

		return new BaseRsp(result);
	}

	@GetMapping()
	public BaseRsp topoStatistic() {
		TopoDigest digest = remoteTopoService.getTopoDigest(testTopoId);

		return new BaseRsp(digest);
	}
}
