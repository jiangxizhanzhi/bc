package com.cyberpeace.rtks.controller.role;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 岗位输出
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class RoleOutput {

	/**
	 * 岗位id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "岗位id")
	private String resourceId;

	/**
	 * 岗位名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "岗位名称")
	private String name;

	/**
	 * 岗位描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "岗位描述")
	private String description;

	/**
	 * 岗位所属层级id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "岗位所属层级id")
	private String levelId;

	/**
	 * 岗位所属层级名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "岗位所属层级名称")
	private String levelName;

	/**
	 * 岗位任务列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "岗位任务列表")
	private List<String> taskIds;

	/**
	 * 岗位任务数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "岗位任务数量")
	private Integer taskCount;

	/**
	 * 岗位知识点列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "岗位知识点列表")
	private List<String> knowledgeIds;

	/**
	 * 岗位知识点权重列表
	 *
	 * @see List<SkillKnowledgeWeight>
	 */
	@ApiModelProperty(value = "岗位知识点权重列表")
	private List<SkillKnowledgeWeight> knowledgeIdWeights;

	/**
	 * 岗位知识点数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "岗位知识点数量")
	private Integer knowledgeCount;

	/**
	 * 岗位技能点列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "岗位技能点列表")
	private List<String> skillIds;

	/**
	 * 岗位技能点权重列表
	 *
	 * @see List<SkillKnowledgeWeight>
	 */
	@ApiModelProperty(value = "岗位技能点权重列表")
	private List<SkillKnowledgeWeight> skillIdWeights;

	/**
	 * 岗位技能点数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "岗位技能点数量")
	private Integer skillCount;

	/**
	 * 岗位技能点任务数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "岗位技能点任务数量")
	private Integer skillMissionCount;

	/**
	 * 岗位能力列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "岗位能力列表")
	private List<String> abilityIds;

	/**
	 * 岗位能力数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "岗位能力数量")
	private Integer abilityCount;

	/**
	 * 岗位知识点课程数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "岗位知识点课程数量")
	private Integer knowledgeCourseCount;

	/**
	 * 头像
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "头像")
	private String avatar;
}
