
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceRoundMechanismRule;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间圆机制统治Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceRoundMechanismRuleBo extends SpaceBoBase {

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
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "简述")
	private String description;

	/**
	 * 圆环长度
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "每轮时长")
	private Integer roundLength;

	/**
	 * 初始化分数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "初始分值")
	private Integer initScore;

	/**
	 * 最大重置时间
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "重置最大次数")
	private Integer maxResetTime;

	/**
	 * 最小重置间隔
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "重置最小间隔")
	private Integer minResetInterval;

	/**
	 * BO2 实体
	 *
	 * @return SpaceRound机构规则
	 * @throws
	 * @see SpaceRoundMechanismRule
	 */
	public SpaceRoundMechanismRule bo2Entity() {
		SpaceRoundMechanismRule entity = BeanUtils.copy(this, SpaceRoundMechanismRule.class);
		return entity;
	}

}
    