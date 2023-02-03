package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SimulatorModifyBo implements Serializable {

	@ApiModelProperty(value = "节点id", required = true)
	private String nodeId;

	@ApiModelProperty(value = "行为变量配置")
	private List<UserInstanceAddBo> userModels;
}
