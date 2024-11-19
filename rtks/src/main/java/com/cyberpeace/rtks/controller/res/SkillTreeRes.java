package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.rtks.service.bo.skill.SkillRes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能树结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SkillTreeRes implements Serializable {

	/**
	 * 类别
	 *
	 * @see String
	 */
	@ApiModelProperty("类别")
	private String category;

	/**
	 * 技能列表
	 *
	 * @see List<SkillRes>
	 */
	@ApiModelProperty("技能列表")
	private List<SkillRes> skillResList;

}