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
 * 岗位输入
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RoleInput implements Serializable {

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
	 * 职责清单
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("职责清单")
	private List<String> taskIds;

	/**
	 * 知识点清单
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("知识点清单")
	private List<String> knowledgeIds;

	/**
	 * 知识点清单+权重
	 *
	 * @see List<SkillKnowledgeWeight>
	 */
	@ApiModelProperty("知识点清单+权重")
	private List<SkillKnowledgeWeight> knowledgeIdWeights = Lists.newArrayList();

	/**
	 * 技能清单+权重
	 *
	 * @see List<IdWeight>
	 */
	@ApiModelProperty("技能清单+权重")
	private List<IdWeight> skillIdWeights = Lists.newArrayList();

	/**
	 * 能力清单（暂无）
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("能力清单（暂无）")
	private List<String> abilityIds = Lists.newArrayList();

	/**
	 * 知识点权重（百分比）
	 *
	 * @see Integer
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
