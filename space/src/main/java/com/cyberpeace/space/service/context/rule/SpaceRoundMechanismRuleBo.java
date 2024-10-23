
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceRoundMechanismRule;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SpaceRoundMechanismRuleBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;
    

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

	public SpaceRoundMechanismRule bo2Entity() {
		SpaceRoundMechanismRule entity = BeanUtils.copy(this, SpaceRoundMechanismRule.class);
		return entity;
	}

}
    