
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRule;
import com.cyberpeace.space.model.SpaceBoBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间流量检测统治Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceTrafficDetectRuleBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "规则内容")
	private String ruleContent;

	@ApiModelProperty(value = "规则编号")
	private String ruleId;

	@ApiModelProperty(value = "规则信息")
	private String ruleInfo;

	@ApiModelProperty(value = "威胁等级")
	private String threatLevel;

	@ApiModelProperty(value = "工具")
	private String tool;

	@ApiModelProperty(value = "防御阶段， type=57")
	private Integer stage;

	@ApiModelProperty(value = "攻击类型， type=58")
	private Integer attackType;

	public SpaceTrafficDetectRule bo2Entity() {
		SpaceTrafficDetectRule entity = BeanUtils.copy(this, SpaceTrafficDetectRule.class);
		return entity;
	}

}
    