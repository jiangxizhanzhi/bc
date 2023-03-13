package com.cyberpeace.integratation.model.situation.node;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeCriticalDegreeContext implements Serializable {

	@ApiModelProperty("节点IP")
	private String nodeIp;

	@ApiModelProperty("节点名称")
	private String nodeName;

	@ApiModelProperty("节点关键度")
	private String criticalDegree;
}
