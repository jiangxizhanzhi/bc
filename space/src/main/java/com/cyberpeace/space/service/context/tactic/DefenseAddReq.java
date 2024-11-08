package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 防御新增请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class DefenseAddReq {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 策略id
	 */
	@ApiModelProperty(value = "战法id")
	private String tacticId;

	/**
	 * 类别id
	 */
	@ApiModelProperty(value = "所属战术")
	private String categoryId;

	/**
	 * 角色
	 */
	@ApiModelProperty(value = "岗位")
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
	@ApiModelProperty(value = "作战环境")
	private String scene;

	/**
	 * 监控
	 */
	@ApiModelProperty(value = "监控")
	private String monitor;

	/**
	 * 分析
	 */
	@ApiModelProperty(value = "分析")
	private String analyze;

	/**
	 * 工具
	 */
	@ApiModelProperty(value = "工具")
	private List<String> tool;
}
