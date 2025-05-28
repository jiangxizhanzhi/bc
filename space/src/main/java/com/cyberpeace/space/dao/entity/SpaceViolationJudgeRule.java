package com.cyberpeace.space.dao.entity;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceViolationJudgeRuleBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间违规裁判规则
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_violation_judge_rule")
public class SpaceViolationJudgeRule extends SpaceBaseTable {

	@ApiModelProperty(value = "提交答案间隔")
	@TableField("`interval`")
	private Integer interval;

	@ApiModelProperty(value = "端口检测")
	private Boolean portCheck;

	@ApiModelProperty(value = "攻击目标检测")
	private Boolean targetCheck;


	public SpaceViolationJudgeRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceViolationJudgeRuleBo bo = BeanUtils.copy(this, SpaceViolationJudgeRuleBo.class);
		return bo;
	}
}
