package com.cyberpeace.rtks.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.controller.res.RelatedCourse;
import com.cyberpeace.rtks.dao.entity.Ability;
import com.cyberpeace.rtks.dao.entity.Knowledge;
import com.cyberpeace.rtks.dao.entity.Skill;
import com.cyberpeace.rtks.dao.entity.Task;

import com.cyberpeace.rtks.service.bo.skill.MissionTemplateSimple;
import com.cyberpeace.rtks.service.bo.skill.SkillRes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleDetailRes {

	public final static String TYPE_SKILL = "skill";// 只传技能关联课程

	public final static String TYPE_KNOWLEDGE = "knowledge";// 只传知识点关联课程

	public final static String TYPE_ALL = "all";// 只传课程学习进展

	public final static String RELATED_COURSE_COURSE = "course";// 只传课程

	public final static String RELATED_COURSE_STUDY = "study";// 只传课程学习进展

	public final static String RELATED_COURSE_ALL = "all";// 同时都传

	/**
	 * 岗位id
	 */
	@ApiModelProperty(value = "岗位id")
	private String resourceId;

	/**
	 * 岗位名称
	 */
	@ApiModelProperty(value = "岗位名称")
	private String name;

	/**
	 * 岗位描述
	 */
	@ApiModelProperty(value = "岗位描述")
	private String description;

	/**
	 * 岗位所属层级id
	 */
	@ApiModelProperty(value = "岗位所属层级id")
	private String levelId;

	/**
	 * 岗位所属完整层级路径
	 */
	@ApiModelProperty(value = "岗位所属完整层级路径")
	private String levelName;

	/**
	 * 技能体系
	 */
	@ApiModelProperty(value = "技能体系")
	private String category;

	/**
	 * 岗位匹配度
	 */
	@ApiModelProperty(value = "岗位匹配度")
	private String percent;

	/**
	 * 岗位任务id列表
	 */
	@ApiModelProperty(value = "岗位任务id列表")
	private List<String> taskIds;

	/**
	 * 岗位任务数量
	 */
	@ApiModelProperty(value = "岗位任务数量")
	private Integer taskCount;

	/**
	 * 岗位知识点id列表
	 */
	@ApiModelProperty(value = "岗位知识点id列表")
	private List<String> knowledgeIds;

	/**
	 * 岗位知识点数量
	 */
	@ApiModelProperty(value = "岗位知识点数量")
	private Integer knowledgeCount;

	/**
	 * 岗位技能点id列表
	 */
	@ApiModelProperty(value = "岗位技能点id列表")
	private List<String> skillIds;

	/**
	 * 岗位技能点数量
	 */
	@ApiModelProperty(value = "岗位技能点数量")
	private Integer skillCount;

	/**
	 * 岗位能力id列表
	 */
	@ApiModelProperty(value = "岗位能力id列表")
	private List<String> abilityIds;

	/**
	 * 岗位能力数量
	 */
	@ApiModelProperty(value = "岗位能力数量")
	private Integer abilityCount;

	/**
	 * 任务列表
	 */
	@ApiModelProperty("任务列表")
	private List<Task> taskDataList;

	/**
	 * 能力列表
	 */
	@ApiModelProperty("能力列表")
	private List<Ability> abilityDataList;

	/**
	 * 知识点列表
	 */
	@ApiModelProperty("知识点列表")
	private List<Knowledge> knowledgeDataList;

	/**
	 * 技能列表
	 */
	@ApiModelProperty("技能列表")
	private List<Skill> skillDataList;

	/**
	 * 知识点列表
	 */
	@ApiModelProperty("知识点列表")
	private List<KnowledgeRes> knowledgeResDataList;

	/**
	 * 技能列表
	 */
	@ApiModelProperty("技能列表")
	private List<SkillRes> skillResDataList;

	/**
	 * 框架子层级
	 */
	@ApiModelProperty("框架子层级")
	private LevelTreeOutput levelTreeOutput;

	/**
	 * 基础课程
	 */
	@ApiModelProperty("基础课程")
	private List<RelatedCourse> basicCourseList;

	/**
	 * 技能课程
	 */
	@ApiModelProperty("技能课程")
	private List<RelatedCourse> skillCourseList;

	/**
	 * 训练任务
	 */
	@ApiModelProperty("训练任务")
	private List<MissionTemplateSimple> missions;
}
