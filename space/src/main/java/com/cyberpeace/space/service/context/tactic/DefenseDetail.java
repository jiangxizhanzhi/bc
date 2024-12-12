package com.cyberpeace.space.service.context.tactic;

import com.cyberpeace.cr.tool.service.context.TacticTool;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 防御详情
 *
 * @author SN
 * @date 2024/11/07
 */
@Getter
@Setter
public class DefenseDetail {

	/**
	 * 资源id
	 */
	private String resourceId;

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
	@ApiModelProperty(value = "所属战术id")
	private String categoryId;

	/**
	 * 类别名称
	 */
	@ApiModelProperty(value = "所属战术名称")
	private String categoryName;

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
	private List<TacticTool> tool;
}
