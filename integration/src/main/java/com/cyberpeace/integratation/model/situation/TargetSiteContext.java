package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TargetSiteContext implements Serializable {

	@ApiModelProperty("站点类型")
	private String targetType;

	@ApiModelProperty("站点ID")
	private String targetId;

	public TargetSiteContext() {}

	public TargetSiteContext(String targetType, String targetId) {
		this.targetType = targetType;
		this.targetId = targetId;
	}

}
