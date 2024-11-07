package com.cyberpeace.rtks.controller.role;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色输出
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RoleOutput {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "岗位id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "岗位名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "岗位描述")
	private String description;

	/**
	 * 水平id
	 */
	@ApiModelProperty(value = "岗位所属层级id")
	private String levelId;

	/**
	 * 水平名称
	 */
	@ApiModelProperty(value = "岗位所属层级名称")
	private String levelName;

	/**
	 * 任务id列表
	 */
	@ApiModelProperty(value = "岗位任务列表")
	private List<String> taskIds;

	/**
	 * 任务计数
	 */
	@ApiModelProperty(value = "岗位任务数量")
	private Integer taskCount;

	/**
	 * 知识id列表
	 */
	@ApiModelProperty(value = "岗位知识点列表")
	private List<String> knowledgeIds;

	/**
	 * 知识id权重
	 */
	@ApiModelProperty(value = "岗位知识点权重列表")
	private List<SkillKnowledgeWeight> knowledgeIdWeights;

	/**
	 * 知识计数
	 */
	@ApiModelProperty(value = "岗位知识点数量")
	private Integer knowledgeCount;

	/**
	 * 技能id列表
	 */
	@ApiModelProperty(value = "岗位技能点列表")
	private List<String> skillIds;

	/**
	 * 技能id权重
	 */
	@ApiModelProperty(value = "岗位技能点权重列表")
	private List<SkillKnowledgeWeight> skillIdWeights;

	/**
	 * 技能计数
	 */
	@ApiModelProperty(value = "岗位技能点数量")
	private Integer skillCount;

	/**
	 * 技能任务计数
	 */
	@ApiModelProperty(value = "岗位技能点任务数量")
	private Integer skillMissionCount;

	/**
	 * 能力id列表
	 */
	@ApiModelProperty(value = "岗位能力列表")
	private List<String> abilityIds;

	/**
	 * 能力计数
	 */
	@ApiModelProperty(value = "岗位能力数量")
	private Integer abilityCount;

	/**
	 * 知识课程计数
	 */
	@ApiModelProperty(value = "岗位知识点课程数量")
	private Integer knowledgeCourseCount;

}
