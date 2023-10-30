package com.cyberpeace.rtks.res;

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

	// 只传技能关联课程
	public static final String TYPE_SKILL = "skill";

	// 只传知识点关联课程
	public static final String TYPE_KNOWLEDGE = "knowledge";

	// 只传课程学习进展
	public static final String TYPE_ALL = "all";

	// 只传课程
	public static final String RELATED_COURSE_COURSE = "course";

	// 只传课程学习进展
	public static final String RELATED_COURSE_STUDY = "study";

	// 同时都传
	public static final String RELATED_COURSE_ALL = "all";

	@ApiModelProperty(value = "岗位id")
	private String resourceId;

	@ApiModelProperty(value = "岗位名称")
	private String name;

	@ApiModelProperty(value = "岗位描述")
	private String description;

	@ApiModelProperty(value = "岗位所属层级id")
	private String levelId;

	@ApiModelProperty(value = "岗位所属完整层级路径")
	private String levelName;

	@ApiModelProperty(value = "技能体系")
	private String category;

	@ApiModelProperty(value = "岗位匹配度")
	private String percent;

	@ApiModelProperty(value = "岗位任务id列表")
	private List<String> taskIds;

	@ApiModelProperty(value = "岗位任务数量")
	private Integer taskCount;

	@ApiModelProperty(value = "岗位知识点id列表")
	private List<String> knowledgeIds;

	@ApiModelProperty(value = "岗位知识点数量")
	private Integer knowledgeCount;

	@ApiModelProperty(value = "岗位技能点id列表")
	private List<String> skillIds;

	@ApiModelProperty(value = "岗位技能点数量")
	private Integer skillCount;

	@ApiModelProperty(value = "岗位能力id列表")
	private List<String> abilityIds;

	@ApiModelProperty(value = "岗位能力数量")
	private Integer abilityCount;

	@ApiModelProperty("任务列表")
	private List<Task> taskDataList;

	@ApiModelProperty("能力列表")
	private List<Ability> abilityDataList;

	@ApiModelProperty("知识点列表")
	private List<Knowledge> knowledgeDataList;

	@ApiModelProperty("技能列表")
	private List<Skill> skillDataList;

	@ApiModelProperty("知识点列表")
	private List<KnowledgeRes> knowledgeResDataList;

	@ApiModelProperty("技能列表")
	private List<SkillRes> skillResDataList;

	@ApiModelProperty("框架子层级")
	private LevelTreeOutput levelTreeOutput;

	@ApiModelProperty("基础课程")
	private List<RelatedCourse> basicCourseList;

	@ApiModelProperty("技能课程")
	private List<RelatedCourse> skillCourseList;

	@ApiModelProperty("训练任务")
	private List<MissionTemplateSimple> missions;
}
