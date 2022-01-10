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

	@ApiModelProperty(value = "阶段", required = true)
	private Integer missionStage;
}
