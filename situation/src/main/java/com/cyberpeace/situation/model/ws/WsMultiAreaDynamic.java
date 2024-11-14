package com.cyberpeace.situation.model.ws;

import java.io.Serializable;

import com.cyberpeace.situation.model.AreaInfo;
import com.cyberpeace.situation.model.ElementInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WsMultiAreaDynamic extends WsMultiNodeDynamic implements Serializable {

	/**
	 * 源节点信息（更新部分）
	 */
	@ApiModelProperty("源节点信息（更新部分）")
	private ElementInfo sourceElementInfo;

	/**
	 * 目的区域信息（更新部分）
	 */
	@ApiModelProperty("目的区域信息（更新部分）")
	private AreaInfo targetAreaInfo;

	public void setNodeInfo(ElementInfo sourceElementInfo, AreaInfo targetAreaInfo) {
		this.sourceElementInfo = sourceElementInfo;
		this.targetAreaInfo = targetAreaInfo;
		this.setSourceId(sourceElementInfo.getElementId());
		this.setTargetId(targetAreaInfo.getAreaId());
	}

}
