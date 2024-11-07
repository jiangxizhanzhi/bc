package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 攻击列表 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class AttackListBo {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "")
	private String name;

	/**
	 * 策略id
	 */
	@ApiModelProperty(value = "")
	private String tacticId;

	/**
	 * 类别id
	 */
	@ApiModelProperty(value = "")
	private String categoryId;

	/**
	 * 类别
	 */
	@ApiModelProperty(value = "")
	private String category;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "")
	private String description;

	/**
	 * 场景
	 */
	@ApiModelProperty(value = "")
	private String scene;

	/**
	 * 特征
	 */
	@ApiModelProperty(value = "")
	private String feature;
}
