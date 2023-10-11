package com.cyberpeace.cr.redvsblue.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class RbTimeReq implements Serializable {

	@ApiModelProperty(required = true, value = "资源id")
	private String resourceId;

	@ApiModelProperty(required = true, value = "资源id")
	@NotNull
	private String roomId;

	@ApiModelProperty(required = true, value = "速率")
	@NotNull
	private Integer rate;

	@ApiModelProperty(required = true, value = "天文时间")
	@NotNull
	private Date astronomicalTime;

	@ApiModelProperty(required = true, value = "作战时间")
	@NotNull
	private Date combatTime;

}