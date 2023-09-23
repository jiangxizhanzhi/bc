package com.cyberpeace.cr.redvsblue.controller;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.cr.redvsblue.service.RbSituationService;
import com.cyberpeace.cr.redvsblue.service.context.RbSituation;
import com.cyberpeace.cr.redvsblue.service.context.RbSituationServer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "红蓝对抗城市沙盘")
@Slf4j
@RestController
@RequestMapping("/cr/red_vs_blue/situation/")
public class RbSituationController {

	@Autowired
	private RbSituationService rbSituationService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@ApiOperation("获取红蓝对抗实例列表")
	@GetMapping("/get_instances")
	public List<RbSituation> getInstances() {
		return rbSituationService.getAllRbSituation();
	}

	@ApiOperation("获取红蓝对抗实例节点数据")
	@GetMapping("/get_servers")
	public List<RbSituationServer> getServers(
			@ApiParam(name = "resource_id", value = "红蓝实例编号") @RequestParam(value = "resource_id", required = true) String resourceId) {
		return rbSituationService.getRbServerByResourceId(resourceId);
	}

}
