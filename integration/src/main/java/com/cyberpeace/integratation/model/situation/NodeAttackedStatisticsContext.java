package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeAttackedStatisticsContext extends NodeBasicInfoContext implements Serializable {

	@ApiModelProperty("控守阵营")
	private String nodeGroup;

	@ApiModelProperty("被攻击数量")
	private Integer attackedCount;

}
