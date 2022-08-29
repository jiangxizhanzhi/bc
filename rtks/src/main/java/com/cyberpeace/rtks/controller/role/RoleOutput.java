package com.cyberpeace.rtks.controller.role;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleOutput {

	@ApiModelProperty(value = "岗位id")
	private String resourceId;

	@ApiModelProperty(value = "岗位名称")
	private String name;

	@ApiModelProperty(value = "岗位描述")
	private String description;

	@ApiModelProperty(value = "岗位所属层级id")
	private String levelId;

	@ApiModelProperty(value = "岗位所属层级名称")
	private String levelName;

	@ApiModelProperty(value = "岗位任务列表")
	private List<String> taskIds;

	@ApiModelProperty(value = "岗位任务数量")
	private Integer taskCount;

	@ApiModelProperty(value = "岗位知识点列表")
	private List<String> knowledgeIds;

	@ApiModelProperty(value = "岗位知识点权重列表")
	private List<SkillKnowledgeWeight> knowledgeIdWeights;

	@ApiModelProperty(value = "岗位知识点数量")
	private Integer knowledgeCount;

	@ApiModelProperty(value = "岗位技能点列表")
	private List<String> skillIds;

	@ApiModelProperty(value = "岗位技能点权重列表")
	private List<SkillKnowledgeWeight> skillIdWeights;

	@ApiModelProperty(value = "岗位技能点数量")
	private Integer skillCount;

	@ApiModelProperty(value = "岗位技能点任务数量")
	private Integer skillMissionCount;

	@ApiModelProperty(value = "岗位能力列表")
	private List<String> abilityIds;

	@ApiModelProperty(value = "岗位能力数量")
	private Integer abilityCount;

	@ApiModelProperty(value = "岗位知识点课程数量")
	private Integer knowledgeCourseCount;

}
