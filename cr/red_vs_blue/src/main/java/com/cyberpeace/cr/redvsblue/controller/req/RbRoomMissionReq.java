package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;

import com.cyberpeace.cr.crbase.controller.req.MissionTemplateReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbRoomMissionReq extends MissionTemplateReq implements Serializable {

	@ApiModelProperty(value = "房间id", required = true)
	private String roomId;

	@ApiModelProperty(value = "角色id", required = true)
	private String roleResourceId;

	@ApiModelProperty(value = "阶段下标", required = true)
	private Integer missionStage;

	@ApiModelProperty(value = "后一个任务id", required = true)
	private String afterMissionId;
}
