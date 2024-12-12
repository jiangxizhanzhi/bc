package com.cyberpeace.space.service.context.strategy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceStrategyFrameAddReq {

	@ApiModelProperty(value = "")
	private String name;

	private String parent;
}
