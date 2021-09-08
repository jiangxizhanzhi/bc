package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.dao.entity.Ability;
import com.cyberpeace.rtks.dao.entity.Knowledge;
import com.cyberpeace.rtks.dao.entity.Skill;
import com.cyberpeace.rtks.dao.entity.Task;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleDetailRes implements Serializable {

	public final static String TYPE_SKILL = "skill";// 只传技能关联课程

	public final static String TYPE_KNOWLEDGE = "knowledge";// 只传知识点关联课程

	public final static String TYPE_ALL = "all";// 只传课程学习进展

	public final static String RELATED_COURSE_COURSE = "course";// 只传课程

	public final static String RELATED_COURSE_STUDY = "study";// 只传课程学习进展

	public final static String RELATED_COURSE_ALL = "all";// 同时都传

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

	private List<String> taskIds;

	private Integer taskCount;

	private List<String> knowledgeIds;

	private Integer knowledgeCount;

	private List<String> skillIds;

	private Integer skillCount;

	private List<String> abilityIds;

	private Integer abilityCount;

	private List<Task> taskDataList;

	private List<Ability> abilityDataList;

	private List<Knowledge> knowledgeDataList;

	private List<Skill> skillDataList;

	private List<KnowledgeRes> knowledgeResDataList;

	private List<SkillRes> skillResDataList;

	private LevelTreeOutput levelTreeOutput;

	@ApiModelProperty("基础课程")
	private List<RelatedCourse> basicCourseList;

	@ApiModelProperty("技能课程")
	private List<RelatedCourse> skillCourseList;
}
