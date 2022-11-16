package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeBo implements Serializable {

	@ApiModelProperty(value = "用户模型id")
	private String userModelId;

	@ApiModelProperty(value = "节点id")
	private String nodeId;

	@ApiModelProperty(value = "节点名称")
	private String nodeName;

	@ApiModelProperty(value = "IP配置")
	private List<IpBo> ipConfig;

	@ApiModelProperty(value = "行为变量配置")
	private List<BehaviorConfig> behaviorConfig;
}
