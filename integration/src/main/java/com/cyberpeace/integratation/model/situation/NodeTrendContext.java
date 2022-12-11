package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeTrendContext implements Serializable {

	@ApiModelProperty(value = "时间点")
	private String time;

	@ApiModelProperty(value = "发起攻击次数")
	private Integer attackCount;

	@ApiModelProperty(value = "遭受攻击次数")
	private Integer attackedCount;

}
