package com.cyberpeace.situation.model.ws;

import java.io.Serializable;

import com.cyberpeace.situation.model.ElementInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WsMultiElementDynamic extends WsMultiNodeDynamic implements Serializable {

	/**
	 * 源节点信息（更新部分）
	 */
	@ApiModelProperty("源节点信息（更新部分）")
	private ElementInfo sourceElementInfo;

	/**
	 * 目的节点信息（更新部分）
	 */
	@ApiModelProperty("目的节点信息（更新部分）")
	private ElementInfo targetElementInfo;

	public void setNodeInfo(ElementInfo sourceElementInfo, ElementInfo targetElementInfo) {
		this.sourceElementInfo = sourceElementInfo;
		this.targetElementInfo = targetElementInfo;
		this.setSourceId(sourceElementInfo.getElementId());
		this.setTargetId(targetElementInfo.getElementId());
	}

}
