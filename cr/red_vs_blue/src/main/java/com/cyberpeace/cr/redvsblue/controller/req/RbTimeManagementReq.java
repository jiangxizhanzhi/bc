package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbTimeManagementReq implements Serializable {

	@ApiModelProperty(required = true)
	@NotNull
	private String roomId;

	private Date astronomicalTime;

	private Date combatTime;

	private Integer rate;
}