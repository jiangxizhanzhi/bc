package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.space.service.context.strategy.SpaceStrategyBo;
import com.cyberpeace.space.service.context.tactic.SpaceTacticBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间行动框架娱乐Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceActionFrameBo
 * @since 1.0.0
 */
@Setter
@Getter
public class SpaceActionFrameRecBo extends SpaceActionFrameBo {

	@ApiModelProperty(value = "框架编排")
	private ActionFrameArrangeBo frame;

	@ApiModelProperty(value = "策略列表")
	private List<SpaceStrategyBo> strategies;

	@ApiModelProperty(value = "战法列表")
	private List<SpaceTacticBo> tactics;

}
