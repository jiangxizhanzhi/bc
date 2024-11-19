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

/**
 * 岗位详情结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class RoleDetailRes {

	/**
	 * 键入 技能
	 *
	 * @see String
	 */
	public final static String TYPE_SKILL = "skill";// 只传技能关联课程

	/**
	 * 类型知识点
	 *
	 * @see String
	 */
	public final static String TYPE_KNOWLEDGE = "knowledge";// 只传知识点关联课程

	/**
	 * 类型全部
	 *
	 * @see String
	 */
	public final static String TYPE_ALL = "all";// 只传课程学习进展

	/**
	 * 相关课程课程
	 *
	 * @see String
	 */
	public final static String RELATED_COURSE_COURSE = "course";// 只传课程

	/**
	 * 相关课程研究
	 *
	 * @see String
	 */
	public final static String RELATED_COURSE_STUDY = "study";// 只传课程学习进展

	/**
	 * 相关课程全部
	 *
	 * @see String
	 */
	public final static String RELATED_COURSE_ALL = "all";// 同时都传

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
	 * 岗位所属完整层级路径
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "岗位所属完整层级路径")
	private String levelName;

	/**
	 * 技能体系
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "技能体系")
	private String category;

	/**
	 * 岗位匹配度
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "岗位匹配度")
	private String percent;

	/**
	 * 岗位任务id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "岗位任务id列表")
	private List<String> taskIds;

	/**
	 * 岗位任务数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "岗位任务数量")
	private Integer taskCount;

	/**
	 * 岗位知识点id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "岗位知识点id列表")
	private List<String> knowledgeIds;

	/**
	 * 岗位知识点数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "岗位知识点数量")
	private Integer knowledgeCount;

	/**
	 * 岗位技能点id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "岗位技能点id列表")
	private List<String> skillIds;

	/**
	 * 岗位技能点数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "岗位技能点数量")
	private Integer skillCount;

	/**
	 * 岗位能力id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "岗位能力id列表")
	private List<String> abilityIds;

	/**
	 * 岗位能力数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "岗位能力数量")
	private Integer abilityCount;

	/**
	 * 任务列表
	 *
	 * @see List<Task>
	 */
	@ApiModelProperty("任务列表")
	private List<Task> taskDataList;

	/**
	 * 能力列表
	 *
	 * @see List<Ability>
	 */
	@ApiModelProperty("能力列表")
	private List<Ability> abilityDataList;

	/**
	 * 知识点列表
	 *
	 * @see List<Knowledge>
	 */
	@ApiModelProperty("知识点列表")
	private List<Knowledge> knowledgeDataList;

	/**
	 * 技能列表
	 *
	 * @see List<Skill>
	 */
	@ApiModelProperty("技能列表")
	private List<Skill> skillDataList;

	/**
	 * 知识点列表
	 *
	 * @see List<KnowledgeRes>
	 */
	@ApiModelProperty("知识点列表")
	private List<KnowledgeRes> knowledgeResDataList;

	/**
	 * 技能列表
	 *
	 * @see List<SkillRes>
	 */
	@ApiModelProperty("技能列表")
	private List<SkillRes> skillResDataList;

	/**
	 * 框架子层级
	 *
	 * @see LevelTreeOutput
	 */
	@ApiModelProperty("框架子层级")
	private LevelTreeOutput levelTreeOutput;

	/**
	 * 基础课程
	 *
	 * @see List<RelatedCourse>
	 */
	@ApiModelProperty("基础课程")
	private List<RelatedCourse> basicCourseList;

	/**
	 * 技能课程
	 *
	 * @see List<RelatedCourse>
	 */
	@ApiModelProperty("技能课程")
	private List<RelatedCourse> skillCourseList;

	/**
	 * 训练任务
	 *
	 * @see List<MissionTemplateSimple>
	 */
	@ApiModelProperty("训练任务")
	private List<MissionTemplateSimple> missions;
}
