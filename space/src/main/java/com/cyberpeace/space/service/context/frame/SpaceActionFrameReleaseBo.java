package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceActionFrameReleaseBo extends SpaceActionFrameReleaseReq {

	@ApiModelProperty(value = "框架名称")
	private String frameName;

	@ApiModelProperty(value = "进度")
	private String progress;

	@ApiModelProperty(value = "下发状态")
	private Boolean released = false;
}
