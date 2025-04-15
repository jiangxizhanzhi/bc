package com.cyberpeace.space.service.context.tactic;

import com.cyberpeace.cr.tool.service.context.TacticTool;
import com.cyberpeace.space.service.context.frame.SpaceActionStrategy;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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


	@ApiModelProperty(value = "行动")
	private List<TacticAction> action;

	@ApiModelProperty(value = "工具")
	private List<TacticTool> tool;

	@ApiModelProperty(value = "策略")
	private List<SpaceActionStrategy> strategy;
}
