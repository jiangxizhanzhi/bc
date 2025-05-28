/**
 * 这段代码定义了一个名为 `ActionFrameArrangeBo` 的类，用于处理行动框架安排的相关业务逻辑。以下是代码的主要功能和结构分析： 
 * ### 类的主要功能 
 * 1. **属性定义**： 
 * - `resourceId`: 框架的唯一标识符。 
 * - `steps`: 一个包含多个 `ActionFrameStep` 对象的列表，表示框架中的各个步骤。 
 * 2. **方法功能**： 
 * - `checkTacticDuplication()`: 
 * - 遍历每个步骤 (`step`) 中的策略 (`tactics`)。 
 * - 检查是否存在重复的策略（通过 `resourceId` 判断）。 
 * - 如果发现重复的策略，抛出 `BusinessException` 异常，提示策略重复。 
 * - 如果某个步骤的 `resourceId` 为空，则为其生成一个新的唯一 ID。 
 * - `resetStatus()`: 
 * - 将所有步骤的状态重置为未发布状态 (`UNRELEASED`)。 
 * - 同时将每个步骤中所有策略的状态也重置为未发布状态。 
 * ### 关键点总结 
 * - **异常处理**：通过 `BusinessException` 处理业务逻辑错误，如策略重复。 
 * - **ID生成**：使用 `IDGeneratorUtil.getID()` 方法生成唯一的 `resourceId`。 
 * - **状态管理**：提供了统一的方法来重置所有步骤及其策略的状态。 
 * ### 使用场景 
 * 该类适用于需要管理复杂行动框架的应用场景，特别是当框架由多个步骤组成，且每个步骤可能包含多个策略时。它确保了框架内的唯一性和状态的一致性。 
**/
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

/**
 * 行动框架安排Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
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
