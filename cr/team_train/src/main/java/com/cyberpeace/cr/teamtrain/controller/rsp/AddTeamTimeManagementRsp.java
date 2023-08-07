package com.cyberpeace.cr.teamtrain.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AddTeamTimeManagementRsp {

	@ApiModelProperty(value = "房间id")
	private String roomId;

	@ApiModelProperty(value = "天文时间")
	private Date astronomicalTime;

	@ApiModelProperty(value = "作战时间")
	private Date combatTime;

	@ApiModelProperty(value = "速率")
	private Integer rate;

	@ApiModelProperty(value = "状态")
	private Integer trainingStatus;
}
