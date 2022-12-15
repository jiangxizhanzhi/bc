package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeBasicInfoContext implements Serializable {

	@ApiModelProperty("节点ID")
	private String nodeId;

	@ApiModelProperty("节点名称")
	private String nodeName;

	@ApiModelProperty("节点类型")
	private String nodeType;

	@ApiModelProperty("节点IP")
	private String nodeIp;

	@ApiModelProperty(value = "物理位置")
	private String location;

}
