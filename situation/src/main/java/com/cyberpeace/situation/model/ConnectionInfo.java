package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ConnectionInfo implements Serializable {

	/**
	 * 链路id
	 */
	@ApiModelProperty("链路id")
	private String connectionId;

	/**
	 * 源节点id
	 */
	@ApiModelProperty("源节点id")
	private String source;

	/**
	 * 目标节点id
	 */
	@ApiModelProperty("目标节点id")
	private String target;

	/**
	 * 连线样式
	 */
	@ApiModelProperty("连线样式")
	private LineStyle style;

	/**
	 * 连线动效
	 */
	@ApiModelProperty("连线动效")
	private List<String> effect;

	/**
	 * 连线状态
	 */
	@ApiModelProperty("连线状态")
	private String status;

}
