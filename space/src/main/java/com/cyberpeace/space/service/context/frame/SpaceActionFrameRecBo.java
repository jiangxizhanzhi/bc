package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.space.service.context.strategy.SpaceStrategyBo;
import com.cyberpeace.space.service.context.tactic.SpaceTacticBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
