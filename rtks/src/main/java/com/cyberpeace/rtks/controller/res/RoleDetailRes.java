package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.rtks.dao.entity.Ability;
import com.cyberpeace.rtks.dao.entity.Task;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleDetailRes implements Serializable {

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

	private List<Task> taskDataList;

	private List<Ability> abilityDataList;

	private List<KnowledgeRes> knowledgeResDataList;

	private List<SkillRes> skillResDataList;

}
