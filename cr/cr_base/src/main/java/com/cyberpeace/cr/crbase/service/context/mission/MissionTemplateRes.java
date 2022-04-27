package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionTemplateRes implements Serializable {

	@ApiModelProperty("任务id")
	private String resourceId;

	@ApiModelProperty("任务名称")
	private String title;

	@ApiModelProperty("场景名称")
	private String sceneName;

	@ApiModelProperty("场景类型")
	private Integer sceneType;

	@ApiModelProperty("场景难度")
	private Integer sceneDifficulty;
}
