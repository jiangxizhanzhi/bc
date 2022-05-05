package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionTemplateSimple implements Serializable {

	@ApiModelProperty("任务id")
	private String resourceId;

	@ApiModelProperty("任务名称")
	private String title;

	@ApiModelProperty("场景名称")
	private String sceneName;
}
