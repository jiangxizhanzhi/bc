package com.cyberpeace.cr.teamtrain.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class TeamTimeManagementReq implements Serializable {

	@ApiModelProperty(required = true, value = "房间id")
	@NotNull
	private String roomId;

	@ApiModelProperty(value = "天文时间")
	private Date astronomicalTime;

	@ApiModelProperty(value = "作战时间")
	private Date combatTime;

	@ApiModelProperty(value = "速率")
	private Integer rate;
}