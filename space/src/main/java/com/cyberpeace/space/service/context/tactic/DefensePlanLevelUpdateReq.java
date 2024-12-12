package com.cyberpeace.space.service.context.tactic;

import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefensePlanLevelUpdateReq {

	@ApiModelProperty(value = "框架层级")
	private SpaceFrameLevel level;

	@ApiModelProperty(value = "加入的id")
	private String resourceId;
}
