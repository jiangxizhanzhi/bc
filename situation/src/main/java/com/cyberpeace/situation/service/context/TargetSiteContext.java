package com.cyberpeace.situation.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TargetSiteContext implements Serializable {

	@ApiModelProperty("目标站点类型")
	private Integer targetType;

	@ApiModelProperty("目标站点ID")
	private String targetId;

	@ApiModelProperty("目标站点名称")
	private String targetName;

	public TargetSiteContext() {}

	public TargetSiteContext(Integer targetType, String targetId, String targetName) {
		this.targetId = targetId;
		this.targetType = targetType;
		this.targetName = targetName;

	}

}
