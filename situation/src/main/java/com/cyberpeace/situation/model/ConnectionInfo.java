package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ConnectionInfo implements Serializable {

	@ApiModelProperty("链路id")
	private String connectionId;

	@ApiModelProperty("源节点id")
	private String source;

	@ApiModelProperty("目标节点id")
	private String target;

	@ApiModelProperty("连线样式")
	private LineStyle style;

	@ApiModelProperty("连线动效")
	private List<String> effect;

	@ApiModelProperty("连线状态")
	private String status;

}
