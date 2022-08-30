package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddTemplateMission implements Serializable {

	@ApiModelProperty(value = "任务id")
	private String resourceId;

	@ApiModelProperty(value = "任务标题")
	private String title;

	@ApiModelProperty(value = "任务描述")
	private String description;

}
