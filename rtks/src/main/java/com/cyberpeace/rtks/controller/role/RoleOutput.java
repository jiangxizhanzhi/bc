package com.cyberpeace.rtks.controller.role;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleOutput {

	private String resourceId;

	@ApiModelProperty(value = "岗位名称")
	private String name;

	@ApiModelProperty(value = "岗位描述")
	private String description;

	@ApiModelProperty(value = "岗位所属层级id")
	private String levelId;

	private String levelName;

	@ApiModelProperty(value = "技能体系")
	private int category;

	private List<String> taskIds;

	private Integer taskCount;

	private List<String> knowledgeIds;

	private Integer knowledgeCount;

	private List<String> skillIds;

	private Integer skillCount;

	private List<String> abilityIds;

	private Integer abilityCount;

	private Integer knowledgeCourseCount;

	private Integer skillCourseCount;

}
