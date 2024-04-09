package com.cyberpeace.scene.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.scene.service.PineappleService;
import com.cyberpeace.scene.service.bo.interactive.PineappleClientRes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SN
 * @date 2024/04/08
 */
@Api(value = "绵羊墙相关", tags = "绵羊墙")
@Slf4j
@RestController
@RequestMapping("/sheepWall")
public class SheepWallController {

	@Autowired
	private PineappleService pineappleService;

	@ApiOperation("获取DWall ws")
	@GetMapping("/DWall")
	public BaseRsp<String> activateDWall() {
		String wsPath = pineappleService.activateDWall();
		return new BaseRsp(wsPath);
	}

	@ApiOperation("获取客户端")
	@GetMapping("/clients")
	public BaseRsp<PineappleClientRes> getClients() {
		PineappleClientRes res = pineappleService.getClients();
		return new BaseRsp(res);
	}

}
