package com.cyberpeace.cr.singletrain.service.bo.mission;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MatrixMission implements Serializable {

	@ApiModelProperty(value = "任务id")
	private String resourceId;

	@ApiModelProperty(value = "任务标题")
	private String title;

	@ApiModelProperty(value = "任务状态, 11:未提交， 1：成功，2：失败")
	private Integer status;
}
