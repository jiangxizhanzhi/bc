package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 攻击列表 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Getter
@Setter
public class AttackListBo {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 策略id
	 */
	@ApiModelProperty(value = "")
	private String tacticId;

	/**
	 * 类别id
	 */
	@ApiModelProperty(value = "类别id")
	private String categoryId;

	/**
	 * 类别
	 */
	@ApiModelProperty(value = "类别")
	private String category;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
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

	/**
	 * 技战法体系id
	 */
	@ApiModelProperty(value = "技战法体系id")
	private String systemId;

	/**
	 * 技战法体系
	 */
	@ApiModelProperty(value = "技战法体系")
	private String system;

	/**
	 * 战法来源
	 */
	@ApiModelProperty(value = "战法来源")
	private String source;

	/**
	 * 行动计数
	 */
	@ApiModelProperty(value = "行动计数")
	private Integer actionCount;

	/**
	 * 工具计数
	 */
	@ApiModelProperty(value = "工具计数")
	private Integer toolCount;
	@ApiModelProperty(value = "是否内置")
	private Boolean builtin;
}
