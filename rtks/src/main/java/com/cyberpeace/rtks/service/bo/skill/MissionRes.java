package com.cyberpeace.rtks.service.bo.skill;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MissionRes implements Serializable {

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

	@ApiModelProperty("任务权重")
	private Integer weight;

	@ApiModelProperty("rtks技能id")
	private String skillId;
}
