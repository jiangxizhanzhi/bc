
package com.cyberpeace.space.service.context.rule;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceViolationJudgeRule;
import com.cyberpeace.space.model.SpaceBoBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间违反法官统治Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceViolationJudgeRuleBo extends SpaceBoBase {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String name;


	/**
	 * 间隔
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "提交答案间隔")
	@TableField("`interval`")
	private Integer interval;

	/**
	 * 端口检查
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "端口检测")
	private Boolean portCheck;

	/**
	 * 目标检查
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "攻击目标检测")
	private Boolean targetCheck;

	/**
	 * BO2 实体
	 *
	 * @return 空间违规裁判规则
	 * @throws
	 * @see SpaceViolationJudgeRule
	 */
	public SpaceViolationJudgeRule bo2Entity() {
		SpaceViolationJudgeRule entity = BeanUtils.copy(this, SpaceViolationJudgeRule.class);
		return entity;
	}

}
    