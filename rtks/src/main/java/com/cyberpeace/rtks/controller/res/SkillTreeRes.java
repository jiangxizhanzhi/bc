package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.rtks.service.bo.skill.SkillRes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkillTreeRes implements Serializable {

	/**
	 * 类别
	 */
	@ApiModelProperty("类别")
	private String category;

	/**
	 * 技能列表
	 */
	@ApiModelProperty("技能列表")
	private List<SkillRes> skillResList;

}