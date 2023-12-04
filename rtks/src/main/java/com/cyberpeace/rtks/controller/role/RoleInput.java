package com.cyberpeace.rtks.controller.role;

import com.cyberpeace.rtks.service.bo.skill.IdWeight;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoleInput implements Serializable {

	@ApiModelProperty(value = "岗位名称")
	private String name;

	@ApiModelProperty(value = "岗位描述")
	private String description;

	@ApiModelProperty(value = "岗位所属层级id")
	private String levelId;

	@ApiModelProperty("职责清单")
	private List<String> taskIds;

	@ApiModelProperty("知识点清单")
	private List<String> knowledgeIds;

	@ApiModelProperty("知识点清单+权重")
	private List<SkillKnowledgeWeight> knowledgeIdWeights = Lists.newArrayList();

	@ApiModelProperty("技能清单+权重")
	private List<IdWeight> skillIdWeights = Lists.newArrayList();

	@ApiModelProperty("能力清单（暂无）")
	private List<String> abilityIds = Lists.newArrayList();

	@ApiModelProperty("知识点权重（百分比）")
	private Integer knowledgeWeight;

}
