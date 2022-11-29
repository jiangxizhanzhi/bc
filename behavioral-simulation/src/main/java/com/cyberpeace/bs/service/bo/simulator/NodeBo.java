package com.cyberpeace.bs.service.bo.simulator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NodeBo implements Serializable {

	@ApiModelProperty(value = "用户模型id", required = true)
	private String userModelId;

	@ApiModelProperty(value = "节点id", required = true)
	private String nodeId;

	@ApiModelProperty(value = "是否实体设备", required = true)
	private Boolean isEntity;

	@ApiModelProperty(value = "节点名称", required = true)
	private String nodeName;

	@ApiModelProperty(value = "IP配置")
	private List<IpBo> ipConfig;

	@ApiModelProperty(value = "行为变量配置")
	private List<BehaviorConfig> behaviorConfig;
}
