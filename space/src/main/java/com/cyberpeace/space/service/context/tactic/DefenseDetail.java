package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DefenseDetail {

	private String resourceId;

	private String name;

	@ApiModelProperty(value = "战法id")
	private String tacticId;

	@ApiModelProperty(value = "所属战术id")
	private String categoryId;

	@ApiModelProperty(value = "所属战术名称")
	private String categoryName;

	@ApiModelProperty(value = "岗位")
	private Integer role;

	@ApiModelProperty(value = "标签")
	private List<String> tag;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "作战环境")
	private String scene;

	@ApiModelProperty(value = "监控")
	private String monitor;

	@ApiModelProperty(value = "分析")
	private String analyze;

	@ApiModelProperty(value = "工具")
	private List<TacticTool> tool;
}
