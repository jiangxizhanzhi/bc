package com.cyberpeace.rtks.controller.role;

import java.util.List;

import com.cyberpeace.rtks.dao.entity.Ability;
import com.cyberpeace.rtks.dao.entity.Knowledge;
import com.cyberpeace.rtks.dao.entity.Task;
import com.cyberpeace.rtks.service.bo.skill.SkillWeightRes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 岗位详情输出
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see RoleOutput
 * @since 1.0.0
 */
@Data
public class RoleDetailOutput extends RoleOutput {

	/**
	 * 职责列表
	 *
	 * @see List<Task>
	 */
	@ApiModelProperty(value = "职责列表")
	private List<Task> taskDataList;

	/**
	 * 知识列表
	 *
	 * @see List<Knowledge>
	 */
	@ApiModelProperty(value = "知识列表")
	private List<Knowledge> knowledgeDataList;

	/**
	 * 技能列表
	 *
	 * @see List<SkillWeightRes>
	 */
	@ApiModelProperty(value = "技能列表")
	private List<SkillWeightRes> skillDataList;

	/**
	 * 能力列表
	 *
	 * @see List<Ability>
	 */
	@ApiModelProperty(value = "能力列表")
	private List<Ability> abilityDataList;

	/**
	 * 知识权重
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "知识权重")
	private Integer knowledgeWeight;

}
