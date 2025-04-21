package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceActionFrameReleaseReq {

	@ApiModelProperty(value = "框架实体id")
	private String applicationId;

	@ApiModelProperty(value = "阶段")
	private String step;

	@ApiModelProperty(value = "用户id")
	private String userId;

	@ApiModelProperty(value = "用户名")
	private String userName;
}
