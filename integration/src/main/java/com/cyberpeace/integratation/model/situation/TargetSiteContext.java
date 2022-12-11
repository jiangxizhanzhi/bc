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

}
