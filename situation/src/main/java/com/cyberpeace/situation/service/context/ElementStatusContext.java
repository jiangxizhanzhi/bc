package com.cyberpeace.situation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ElementStatusContext extends StatusContext {

	/**
	 * 节点id
	 */
	@ApiModelProperty("节点id")
	private String elementId;

	public void setNodeId(String nodeId) {
		this.elementId = nodeId;
	}

}
