package com.cyberpeace.space.dao.entity;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceRoundMechanismRuleBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("space_round_mechanism_rule")
public class SpaceRoundMechanismRule extends SpaceBaseTable {

	@ApiModelProperty(value = "简述")
	private String description;

	@ApiModelProperty(value = "每轮时长")
	private Integer roundLength;

	@ApiModelProperty(value = "初始分值")
	private Integer initScore;

	@ApiModelProperty(value = "重置最大次数")
	private Integer maxResetTime;

	@ApiModelProperty(value = "重置最小间隔")
	private Integer minResetInterval;


	public SpaceRoundMechanismRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceRoundMechanismRuleBo bo = BeanUtils.copy(this, SpaceRoundMechanismRuleBo.class);
		return bo;
	}
}
