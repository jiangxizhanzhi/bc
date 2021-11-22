package com.cyberpeace.demo.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;

import io.swagger.annotations.Api;

@Api(tags = "测试接口")
@RestController
@RequestMapping("/test")
@RequiresAuthentication
public class TestController {

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private RemoteSceneService remoteSceneService;

	String testTopoId = "1dd035784f5b44869c4e96aa07c31fd5";

	@PostMapping("/copytopo")
	public BaseRsp createTopo() {
		String result = remoteTopoService.copyTopo(testTopoId);

		return new BaseRsp(result);
	}

	@GetMapping("topoStatistic")
	public BaseRsp topoStatistic() {
		TopoDigest digest = remoteTopoService.getTopoDigest(testTopoId);

		return new BaseRsp(digest);
	}

	@GetMapping("/startScene")
	public BaseRsp<String> startScene() {
		String sceneId = remoteSceneService.startScene(testTopoId, null);
		return new BaseRsp<>(sceneId);
	}

	@GetMapping("/stopScene")
	public BaseRsp<Boolean> stopScene() {
		Boolean result = remoteSceneService.stopScene("b3304280b0a14d5aa7cd4dcf03ddd8f", null);

		return new BaseRsp<>(result);
	}
}
