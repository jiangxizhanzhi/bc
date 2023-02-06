package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RbMissionStatusGroupDataRsp implements Serializable {


	@ApiModelProperty(value = "任务名称")
	private String task;

	@ApiModelProperty(value = "完成状态")
	private Integer status;

}
