package com.cyberpeace.rtks.controller.role;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleInput implements Serializable {

	@ApiModelProperty(value = "岗位名称")
	private String name;

	@ApiModelProperty(value = "岗位描述")
	private String description;

	@ApiModelProperty(value = "岗位所属层级id")
	private String levelId;

	@ApiModelProperty(value = "技能体系")
	private Integer category;

	private List<String> taskIds;

	private List<String> knowledgeIds;

	private List<String> skillIds;

	private List<String> abilityIds;

}
