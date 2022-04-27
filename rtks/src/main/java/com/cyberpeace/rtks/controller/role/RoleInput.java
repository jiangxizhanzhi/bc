package com.cyberpeace.rtks.controller.role;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.rtks.service.bo.skill.IdWeight;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleInput implements Serializable {

	@ApiModelProperty(value = "岗位名称")
	private String name;

	@ApiModelProperty(value = "岗位描述")
	private String description;

	@ApiModelProperty(value = "岗位所属层级id")
	private String levelId;

	private List<String> taskIds;

	private List<String> knowledgeIds;

	private List<SkillKnowledgeWeight> knowledgeIdWeights = Lists.newArrayList();

	private List<String> skillIds = Lists.newArrayList();

	private List<IdWeight> skillIdWeights = Lists.newArrayList();

	private List<String> abilityIds = Lists.newArrayList();

	private Integer knowledgeWeight;

}
