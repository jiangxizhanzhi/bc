
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceEventIntegrationRule;
import com.cyberpeace.space.model.SpaceBoBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间事件集成统治Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceEventIntegrationRuleBo extends SpaceBoBase {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 类型
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "检测类型")
	private String type;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "内容简介")
	private String description;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "内容详情")
	private String content;

	/**
	 * 工具
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "应用工具")
	private String tool;

	/**
	 * 阶段
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "防御阶段， type=57")
	private Integer stage;

	/**
	 * BO2 实体
	 *
	 * @return 空间事件集成规则
	 * @throws
	 * @see SpaceEventIntegrationRule
	 */
	public SpaceEventIntegrationRule bo2Entity() {
		SpaceEventIntegrationRule entity = BeanUtils.copy(this, SpaceEventIntegrationRule.class);
		return entity;
	}

}
    