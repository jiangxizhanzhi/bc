package com.cyberpeace.cs.service.context;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneFlowContext implements Serializable {

	@ApiModelProperty("流量Id")
	private String flowId;

	@ApiModelProperty("包含该流量的节点列表")
	private List<String> nodeIds;

}
