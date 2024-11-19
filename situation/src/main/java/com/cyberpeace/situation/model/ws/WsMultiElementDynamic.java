package com.cyberpeace.situation.model.ws;

import java.io.Serializable;

import com.cyberpeace.situation.model.ElementInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * WS 多元素动态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see WsMultiNodeDynamic
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class WsMultiElementDynamic extends WsMultiNodeDynamic implements Serializable {

	/**
	 * 源节点信息（更新部分）
	 *
	 * @see ElementInfo
	 */
	@ApiModelProperty("源节点信息（更新部分）")
	private ElementInfo sourceElementInfo;

	/**
	 * 目的节点信息（更新部分）
	 *
	 * @see ElementInfo
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
