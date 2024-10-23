
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.space.model.SpaceBaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
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
}