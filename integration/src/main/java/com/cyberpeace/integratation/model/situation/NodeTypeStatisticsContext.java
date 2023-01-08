package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeTypeStatisticsContext implements Serializable {

	@ApiModelProperty("节点类型")
	private String nodeType;

	@ApiModelProperty("数量")
	private Integer count;

	public NodeTypeStatisticsContext() {}

	public NodeTypeStatisticsContext(String typeName, Integer value) {

		this.nodeType = typeName;
		this.count = value;
	}

}
