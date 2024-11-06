
package com.cyberpeace.space.service.context.tactic;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceTacticDefensePlan;
import com.cyberpeace.space.model.SpaceBoBase;
import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

@Data
public class SpaceTacticDefensePlanBo extends SpaceBoBase {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "简述")
	private String description;

	@ApiModelProperty(value = "层级框架")
	private SpaceFrameLevel level;

	public SpaceTacticDefensePlan bo2Entity() {
		SpaceTacticDefensePlan entity = BeanUtils.copy(this, SpaceTacticDefensePlan.class);
		this.level.checkLevel(true);
		List<String> defenseIds = Lists.newArrayList();
		for (SpaceFrameLevel category : level.getChildren()) {
			for (SpaceFrameLevel defense : category.getChildren()) {
				defenseIds.add(defense.getResourceId());
			}
		}
		entity.setPlan(JsonUtil.arrayToString(defenseIds));
		return entity;
	}

}
    