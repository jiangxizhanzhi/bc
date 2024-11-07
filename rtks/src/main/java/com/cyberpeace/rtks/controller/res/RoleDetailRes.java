package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.dao.entity.Ability;
import com.cyberpeace.rtks.dao.entity.Knowledge;
import com.cyberpeace.rtks.dao.entity.Skill;
import com.cyberpeace.rtks.dao.entity.Task;
import com.cyberpeace.rtks.service.bo.skill.MissionTemplateSimple;
import com.cyberpeace.rtks.service.bo.skill.SkillRes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色详情结果
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RoleDetailRes implements Serializable {

	/**
	 * 键入 技能
	 */
	public final static String TYPE_SKILL = "skill";// 只传技能关联课程

	/**
	 * 类型知识
	 */
	public final static String TYPE_KNOWLEDGE = "knowledge";// 只传知识点关联课程

	/**
	 * 键入全部
	 */
	public final static String TYPE_ALL = "all";// 只传课程学习进展

	/**
	 * 相关课程 课程
	 */
	public final static String RELATED_COURSE_COURSE = "course";// 只传课程

	/**
	 * 相关课程学习
	 */
	public final static String RELATED_COURSE_STUDY = "study";// 只传课程学习进展

	/**
	 * 相关课程 全部
	 */
	public final static String RELATED_COURSE_ALL = "all";// 同时都传

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
	@ApiModelProperty(value = "岗位所属完整层级路径")
	private String levelName;

	/**
	 * 类别
	 */
	@ApiModelProperty(value = "技能体系")
	private String category;

	/**
	 * 百分之
	 */
	@ApiModelProperty(value = "岗位匹配度")
	private String percent;

	/**
	 * 任务id列表
	 */
	@ApiModelProperty(value = "岗位任务id列表")
	private List<String> taskIds;

	/**
	 * 任务计数
	 */
	@ApiModelProperty(value = "岗位任务数量")
	private Integer taskCount;

	/**
	 * 知识id列表
	 */
	@ApiModelProperty(value = "岗位知识点id列表")
	private List<String> knowledgeIds;

	/**
	 * 知识计数
	 */
	@ApiModelProperty(value = "岗位知识点数量")
	private Integer knowledgeCount;

	/**
	 * 技能id列表
	 */
	@ApiModelProperty(value = "岗位技能点id列表")
	private List<String> skillIds;

	/**
	 * 技能计数
	 */
	@ApiModelProperty(value = "岗位技能点数量")
	private Integer skillCount;

	/**
	 * 能力id列表
	 */
	@ApiModelProperty(value = "岗位能力id列表")
	private List<String> abilityIds;

	/**
	 * 能力计数
	 */
	@ApiModelProperty(value = "岗位能力数量")
	private Integer abilityCount;

	/**
	 * 任务数据列表
	 */
	@ApiModelProperty("任务列表")
	private List<Task> taskDataList;

	/**
	 * 能力数据列表
	 */
	@ApiModelProperty("能力列表")
	private List<Ability> abilityDataList;

	/**
	 * 知识数据列表
	 */
	@ApiModelProperty("知识点列表")
	private List<Knowledge> knowledgeDataList;

	/**
	 * 技能数据列表
	 */
	@ApiModelProperty("技能列表")
	private List<Skill> skillDataList;

	/**
	 * 知识结果数据列表
	 */
	@ApiModelProperty("知识点列表")
	private List<KnowledgeRes> knowledgeResDataList;

	/**
	 * 技能结果数据列表
	 */
	@ApiModelProperty("技能列表")
	private List<SkillRes> skillResDataList;

	/**
	 * Level Tree 输出
	 */
	@ApiModelProperty("框架子层级")
	private LevelTreeOutput levelTreeOutput;

	/**
	 * 基础课程列表
	 */
	@ApiModelProperty("基础课程")
	private List<RelatedCourse> basicCourseList;

	/**
	 * 技能课程列表
	 */
	@ApiModelProperty("技能课程")
	private List<RelatedCourse> skillCourseList;

	/**
	 * 任务
	 */
	@ApiModelProperty("训练任务")
	private List<MissionTemplateSimple> missions;
}
