package com.cyberpeace.cr.redvsblue.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.*;
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

	@ApiOperation("获取红蓝对抗队伍得分")
	@GetMapping("/score")
	public BaseRsp<RbSituationScoreRsp> score(
			@ApiParam(value = "红蓝对抗实例Id", required = true) @RequestParam(value = "room_id") String roomId,
			@ApiParam(value = "角色类型, 红=4;蓝=5", required = true) @RequestParam(value = "role_type") Integer roleType) {
		return new BaseRsp<>(rbSituationService.getTeamScore(roomId, roleType));
	}

	@ApiOperation("获取红蓝对抗任务状态")
	@GetMapping("/mission_status")
	public BaseRsp<List<RbSituationMissionStatusRsp>> missionStatus(
			@ApiParam(value = "红蓝对抗实例Id", required = true) @RequestParam(value = "room_id") String roomId,
			@ApiParam(value = "角色类型, 红=4;蓝=5", required = true) @RequestParam(value = "role_type") Integer roleType) {
		return new BaseRsp<>(rbSituationService.getMissionStatus(roomId, roleType));
	}

	@ApiOperation("攻击事件")
	@GetMapping("/ad_event")
	public BaseRsp<List<RbSituationAdEventRsp>> adEvent(
			@ApiParam(value = "红蓝对抗实例Id", required = true) @RequestParam(value = "room_id") String roomId,
			@ApiParam(value = "角色类型, 红=4;蓝=5", required = true) @RequestParam(value = "role_type") Integer roleType) {
		return new BaseRsp<>(rbSituationService.getAdEvent(roomId, roleType));
	}

	@ApiOperation("获取红蓝对抗资产状态")
	@GetMapping("/asset_status")
	public BaseRsp<RbSituationAssetStatusRsp> assetStatus(
			@ApiParam(value = "红蓝对抗实例Id", required = true) @RequestParam(value = "room_id") String roomId,
			@ApiParam(value = "角色类型, 红=4;蓝=5", required = true) @RequestParam(value = "role_type") Integer roleType) {
		return new BaseRsp<>(rbSituationService.getAssetStatus(roomId, roleType));
	}

	@ApiOperation("获取红蓝对抗队伍动态")
	@GetMapping("/team_action")
	public BaseRsp<List<RbSituationActionRsp>> teamAction(
			@ApiParam(value = "红蓝对抗实例Id", required = true) @RequestParam(value = "room_id") String roomId,
			@ApiParam(value = "角色类型, 红=4;蓝=5", required = true) @RequestParam(value = "role_type") Integer roleType) {
		return new BaseRsp<>(rbSituationService.getTeamAction(roomId, roleType));
	}

	@ApiOperation("平台动态")
	@GetMapping("/platform_action")
	public BaseRsp<List<RbSituationActionRsp>> platformAction(
			@ApiParam(value = "红蓝对抗实例Id", required = true) @RequestParam(value = "room_id") String roomId,
			@ApiParam(value = "角色类型, 红=4;蓝=5", required = true) @RequestParam(value = "role_type") Integer roleType) {
		return new BaseRsp<>(rbSituationService.getPlatformAction(roomId, roleType));
	}

}
