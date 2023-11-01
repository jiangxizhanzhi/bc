package com.cyberpeace.situation.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationConfigFilterReq implements Serializable {

	@ApiModelProperty(value = "作用域id（拓扑/场景）")
	private String scope;

	@ApiModelProperty(value = "元素id")
	private String nodeId;

	@ApiModelProperty(value = "元素名称")
	private String nodeName;

	@ApiModelProperty(value = "元素类型 1-区域 2-节点")
	private Integer nodeType;

	public SituationConfigFilterReq(String scope) {
		this.scope = scope;
	}

}
