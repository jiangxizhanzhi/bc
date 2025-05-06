package com.cyberpeace.space.service.context.frame;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.space.constant.SpaceCommonConstant;
import com.cyberpeace.space.constant.SpaceError;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionFrameArrangeBo {

	@ApiModelProperty(value = "框架id")
	private String resourceId;

	@ApiModelProperty(value = "框架id")
	private List<ActionFrameStep> steps = Lists.newArrayList();

	public void checkTacticDuplication() {
		for (ActionFrameStep step : steps) {
			Set<String> tacticIds = new HashSet<>();
			if (null == step.getTactics()) {
				continue;
			}
			for (ActionFrameTactic tactic : step.getTactics()) {
				if (tacticIds.contains(tactic.getResourceId())) {
					throw new BusinessException(SpaceError.TACTIC_DUPLICATED);
				} else {
					tacticIds.add(tactic.getResourceId());
				}
			}
			if (StringUtils.isBlank(step.getResourceId())) {
				step.setResourceId(IDGeneratorUtil.getID());
			}
		}
	}

	public void resetStatus() {
		if (steps == null) {
			return;
		}
		for (ActionFrameStep step : steps) {
			step.setStatus(SpaceCommonConstant.OrderStatus.UNRELEASED);
			if (step.getTactics() == null) {
				continue;
			}
			for (ActionFrameTactic tactic : step.getTactics()) {
				tactic.setStatus(SpaceCommonConstant.OrderStatus.UNRELEASED);
			}
		}
	}
}
