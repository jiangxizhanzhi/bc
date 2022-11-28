package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SimulatorAddBo implements Serializable {

	@ApiModelProperty(value = "场景id", required = true)
	private String sceneId;

	@ApiModelProperty(value = "场景名称", required = true)
	private String sceneName;

	@ApiModelProperty(value = "用户节点列表")
	private List<NodeBo> nodeList;
}
