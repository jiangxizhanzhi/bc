package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionModifyReq implements Serializable {

	@ApiModelProperty(value = "标题", required = true)
	private String title;

	@ApiModelProperty(value = "任务id", required = true)
	private String resourceId;

	@ApiModelProperty(value = "分值", required = true)
	private Integer score;

	@ApiModelProperty(value = "技能点", required = true)
	private String skillPoints;

}