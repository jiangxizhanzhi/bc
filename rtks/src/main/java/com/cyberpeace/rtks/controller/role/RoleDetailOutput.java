package com.cyberpeace.rtks.controller.role;

import java.util.List;

import com.cyberpeace.rtks.dao.entity.Ability;
import com.cyberpeace.rtks.dao.entity.Knowledge;
import com.cyberpeace.rtks.dao.entity.Task;
import com.cyberpeace.rtks.service.bo.skill.SkillWeightRes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleDetailOutput extends RoleOutput {

	/**
	 * 职责列表
	 */
	@ApiModelProperty(value = "职责列表")
	private List<Task> taskDataList;

	/**
	 * 知识列表
	 */
	@ApiModelProperty(value = "知识列表")
	private List<Knowledge> knowledgeDataList;

	/**
	 * 技能列表
	 */
	@ApiModelProperty(value = "技能列表")
	private List<SkillWeightRes> skillDataList;

	/**
	 * 能力列表
	 */
	@ApiModelProperty(value = "能力列表")
	private List<Ability> abilityDataList;

	/**
	 * 知识权重
	 */
	@ApiModelProperty(value = "知识权重")
	private Integer knowledgeWeight;

}
