package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbMissionDeleteReq implements Serializable {

	@ApiModelProperty(value = "房间id", required = true)
	private String roomId;

	@ApiModelProperty(value = "任务删除对象列表", required = true)
	private List<MissionConfig> missionConfig;

}
