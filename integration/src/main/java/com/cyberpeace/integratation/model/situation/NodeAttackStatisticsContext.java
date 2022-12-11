package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeAttackStatisticsContext extends NodeBasicInfoContext implements Serializable {

	@ApiModelProperty("控守阵营")
	private String nodeGroup;

	@ApiModelProperty("攻击数量")
	private Integer attackCount;

}
