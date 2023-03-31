package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlatformLinkContext implements Serializable {

	@ApiModelProperty("源平台ID")
	private String sourcePlatformId;

	@ApiModelProperty("目标平台ID")
	private String targetPlatformId;

}
