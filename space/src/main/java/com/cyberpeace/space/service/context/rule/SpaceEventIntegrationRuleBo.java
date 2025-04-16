
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceEventIntegrationRule;
import com.cyberpeace.space.model.SpaceBoBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceEventIntegrationRuleBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "检测类型")
	private String type;

	@ApiModelProperty(value = "内容简介")
	private String description;

	@ApiModelProperty(value = "内容详情")
	private String content;

	@ApiModelProperty(value = "应用工具")
	private String tool;

	@ApiModelProperty(value = "防御阶段， type=57")
	private Integer stage;

	public SpaceEventIntegrationRule bo2Entity() {
		SpaceEventIntegrationRule entity = BeanUtils.copy(this, SpaceEventIntegrationRule.class);
		return entity;
	}

}
    