package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionTemplateSimple implements Serializable {

	/**
	 * 任务id
	 */
	@ApiModelProperty("任务id")
	private String resourceId;

	/**
	 * 任务名称
	 */
	@ApiModelProperty("任务名称")
	private String title;

	/**
	 * 场景名称
	 */
	@ApiModelProperty("场景名称")
	private String sceneName;
}
