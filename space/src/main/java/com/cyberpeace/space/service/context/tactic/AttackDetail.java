package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AttackDetail {

	private String resourceId;

	private String name;

	@ApiModelProperty(value = "ID")
	private String tacticId;

	@ApiModelProperty(value = "类别")
	private String categoryId;

	private String categoryName;

	@ApiModelProperty(value = "岗位，常量type=45")
	private Integer role;

	@ApiModelProperty(value = "标签")
	private List<String> tag;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "技术场景")
	private String scene;

	@ApiModelProperty(value = "拓扑特征")
	private String feature;

	@ApiModelProperty(value = "行动")
	private List<TacticAction> action;

	@ApiModelProperty(value = "工具")
	private List<TacticTool> tool;
}
