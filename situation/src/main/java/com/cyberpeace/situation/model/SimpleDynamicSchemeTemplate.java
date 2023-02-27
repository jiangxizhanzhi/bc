package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SimpleDynamicSchemeTemplate implements Serializable {

	@ApiModelProperty("资源名称")
	private String resourceId;

	@ApiModelProperty("模板名称")
	private String schemeName;

	@ApiModelProperty("阶段数")
	private Integer stageCount;

	@ApiModelProperty("事件数")
	private Integer messageCount;
}
