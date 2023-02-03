package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeBo implements Serializable {

	@ApiModelProperty(value = "节点id", required = true)
	private String nodeId;

	@ApiModelProperty(value = "节点操作系统类型", required = true)
	private Integer type;

	@ApiModelProperty(value = "是否实体设备", required = true)
	private Boolean isEntity;

	@ApiModelProperty(value = "节点名称", required = true)
	private String nodeName;

	@ApiModelProperty(value = "IP配置")
	private List<IpBo> ipConfig;

	@ApiModelProperty(value = "行为变量配置")
	private List<UserInstanceAddBo> userModels;
}
