package com.cyberpeace.situation.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationConfigFilterReq implements Serializable {

	/**
	 * 作用域id（拓扑/场景）
	 */
	@ApiModelProperty(value = "作用域id（拓扑/场景）")
	private String scope;

	/**
	 * 元素id
	 */
	@ApiModelProperty(value = "元素id")
	private String nodeId;

	/**
	 * 元素名称
	 */
	@ApiModelProperty(value = "元素名称")
	private String nodeName;

	/**
	 * 元素类型 1-区域 2-节点
	 */
	@ApiModelProperty(value = "元素类型 1-区域 2-节点")
	private Integer nodeType;

	public SituationConfigFilterReq() {};

	public SituationConfigFilterReq(String scope) {
		this.scope = scope;
	};

}
