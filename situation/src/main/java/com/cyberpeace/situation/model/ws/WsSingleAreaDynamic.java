package com.cyberpeace.situation.model.ws;

import java.io.Serializable;

import com.cyberpeace.situation.model.AreaInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WsSingleAreaDynamic extends WsSingleNodeDynamic implements Serializable {

	/**
	 * 目的区域信息（更新部分）
	 */
	@ApiModelProperty("目的区域信息（更新部分）")
	private AreaInfo targetAreaInfo;

	public void setNodeInfo(AreaInfo targetAreaInfo) {
		this.targetAreaInfo = targetAreaInfo;
		this.setTargetId(targetAreaInfo.getAreaId());
	}

}
