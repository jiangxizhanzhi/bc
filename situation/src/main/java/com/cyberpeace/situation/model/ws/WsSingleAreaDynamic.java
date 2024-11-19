package com.cyberpeace.situation.model.ws;

import java.io.Serializable;

import com.cyberpeace.situation.model.AreaInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * WS 单区域动态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see WsSingleNodeDynamic
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class WsSingleAreaDynamic extends WsSingleNodeDynamic implements Serializable {

	/**
	 * 目的区域信息（更新部分）
	 *
	 * @see AreaInfo
	 */
	@ApiModelProperty("目的区域信息（更新部分）")
	private AreaInfo targetAreaInfo;

	public void setNodeInfo(AreaInfo targetAreaInfo) {
		this.targetAreaInfo = targetAreaInfo;
		this.setTargetId(targetAreaInfo.getAreaId());
	}

}
