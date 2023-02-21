package com.cyberpeace.cr.redvsblue.service.context;

import com.cyberpeace.cr.redvsblue.controller.rsp.RbMissionStatusGroupDataRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MissionStatusWs {

	@ApiModelProperty(value = "情报侦察任务")
	private RbCombatLog investigationMission;

	@ApiModelProperty(value = "防御阶段任务")
	private List<RbCombatLog> defenseStageMissions;

	@ApiModelProperty(value = "攻击阶段任务")
	private RbCombatLog attackStageMission;

	@ApiModelProperty(value = "满分任务")
	private RbMissionStatusGroupDataRsp fullScoreMissionStatus;

	@ApiModelProperty(value = "消息类型")
	private Integer code;
}
