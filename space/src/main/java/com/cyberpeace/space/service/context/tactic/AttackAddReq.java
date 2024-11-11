package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 攻击新增请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class AttackAddReq {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 策略id
	 */
	@ApiModelProperty(value = "ID")
	private String tacticId;

	/**
	 * 类别id
	 */
	@ApiModelProperty(value = "类别")
	private String categoryId;

	/**
	 * 岗位
	 */
	@ApiModelProperty(value = "岗位，常量type=45")
	private Integer role;

	/**
	 * 标记
	 */
	@ApiModelProperty(value = "标签")
	private List<String> tag;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 场景
	 */
	@ApiModelProperty(value = "技术场景")
	private String scene;

	/**
	 * 特征
	 */
	@ApiModelProperty(value = "拓扑特征")
	private String feature;

	/**
	 * 行动
	 */
	@ApiModelProperty(value = "行动")
	private List<TacticAction> action;

	/**
	 * 工具
	 */
	@ApiModelProperty(value = "工具")
	private List<String> tool;
}
