package com.cyberpeace.space.service.context.strategy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpaceStrategyFrameAddReq {

	@ApiModelProperty(value = "")
	private String name;

	private String parent;
}
