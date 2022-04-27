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

	@ApiModelProperty(value = "职责列表")
	private List<Task> taskDataList;

	@ApiModelProperty(value = "知识列表")
	private List<Knowledge> knowledgeDataList;

	@ApiModelProperty(value = "技能列表")
	private List<SkillWeightRes> skillDataList;

	@ApiModelProperty(value = "能力列表")
	private List<Ability> abilityDataList;

}
