package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceViolationJudgeRuleBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("space_violation_judge_rule")
public class SpaceViolationJudgeRule extends SpaceBaseTable {

	@ApiModelProperty(value = "提交答案间隔")
	@TableField("`interval`")
	private Integer interval;

	@ApiModelProperty(value = "端口检测")
	private Boolean portCheck;

	@ApiModelProperty(value = "攻击目标检测")
	private Boolean targetCheck;


	public SpaceViolationJudgeRuleBo entity2Bo() {
		SpaceViolationJudgeRuleBo bo = BeanUtils.copy(this, SpaceViolationJudgeRuleBo.class);
		return bo;
	}
}
