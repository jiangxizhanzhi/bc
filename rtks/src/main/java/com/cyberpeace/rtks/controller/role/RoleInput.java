package com.cyberpeace.rtks.controller.role;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.service.bo.skill.IdWeight;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色输入
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RoleInput implements Serializable {

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
	 * 任务id列表
	 */
	@ApiModelProperty("职责清单")
	private List<String> taskIds;

	/**
	 * 知识id列表
	 */
	@ApiModelProperty("知识点清单")
	private List<String> knowledgeIds;

	/**
	 * 知识id权重
	 */
	@ApiModelProperty("知识点清单+权重")
	private List<SkillKnowledgeWeight> knowledgeIdWeights = Lists.newArrayList();

	/**
	 * 技能id权重
	 */
	@ApiModelProperty("技能清单+权重")
	private List<IdWeight> skillIdWeights = Lists.newArrayList();

	/**
	 * 能力id列表
	 */
	@ApiModelProperty("能力清单（暂无）")
	private List<String> abilityIds = Lists.newArrayList();

	/**
	 * 知识权重
	 */
	@ApiModelProperty("知识点权重（百分比）")
	private Integer knowledgeWeight;

	public void validate() {
		knowledgeIdWeights.forEach((knowledgeIdWeight) -> {
			CrCommonValidator.validateRange(knowledgeIdWeight.getWeight(),
					CrCommonConstants.MIN_SKILL_WEIGHT, CrCommonConstants.MAX_SKILL_WEIGHT,
					RtksBusinessErrorCode.INVALID_WEIGHT);
		});
		skillIdWeights.forEach((skillIdWeight) -> {
			CrCommonValidator.validateRange(skillIdWeight.getWeight(),
					CrCommonConstants.MIN_SKILL_WEIGHT, CrCommonConstants.MAX_SKILL_WEIGHT,
					RtksBusinessErrorCode.INVALID_WEIGHT);
		});

		if (description != null && description.length() > 0) {
			CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
		}

		if (name != null) {
			CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		}
	}

}
