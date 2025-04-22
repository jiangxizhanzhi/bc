package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.space.constant.SpaceError;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		}
	}
}
