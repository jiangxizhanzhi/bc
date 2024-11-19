package com.cyberpeace.situation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 元素状态上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see StatusContext
 * @since 1.0.0
 */
@Data
public class ElementStatusContext extends StatusContext {

	/**
	 * 节点id
	 *
	 * @see String
	 */
	@ApiModelProperty("节点id")
	private String elementId;

	public void setNodeId(String nodeId) {
		this.elementId = nodeId;
	}

}
