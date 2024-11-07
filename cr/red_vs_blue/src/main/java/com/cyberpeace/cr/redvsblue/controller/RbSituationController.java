package com.cyberpeace.cr.redvsblue.controller;

import com.cyberpeace.cr.redvsblue.service.RbSituationService;
import com.cyberpeace.cr.redvsblue.service.context.RbSituation;
import com.cyberpeace.cr.redvsblue.service.context.RbSituationServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RB 情况控制器
 *
 * @author SN
 * @date 2024/11/07
 */
@Api(tags = "红蓝对抗城市沙盘")
@Slf4j
@RestController
@RequestMapping("/cr/red_vs_blue/situation/")
public class RbSituationController {

	/**
	 * RB情况Service
	 */
	@Autowired
	private RbSituationService rbSituationService;

	/**
	 * 兔子模板
	 */
	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * 获取实例
	 *
	 * @return {@code List<RbSituation> }
	 */
	@ApiOperation("获取红蓝对抗实例列表")
	@GetMapping("/get_instances")
	public List<RbSituation> getInstances() {
		return rbSituationService.getAllRbSituation();
	}

	/**
	 * 获取服务器
	 *
	 * @param resourceId 资源id
	 * @return {@code List<RbSituationServer> }
	 */
	@ApiOperation("获取红蓝对抗实例节点数据")
	@GetMapping("/get_servers")
	public List<RbSituationServer> getServers(
			@ApiParam(name = "resource_id", value = "红蓝实例编号") @RequestParam(value = "resource_id", required = true) String resourceId) {
		return rbSituationService.getRbServerByResourceId(resourceId);
	}

}
