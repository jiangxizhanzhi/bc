package com.cyberpeace.space.service.context.tactic;

import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 防御计划层级更新请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class DefensePlanLevelUpdateReq {

	/**
	 * 层级
	 *
	 * @see SpaceFrameLevel
	 */
	@ApiModelProperty(value = "框架层级")
	private SpaceFrameLevel level;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "加入的id")
	private String resourceId;
}
