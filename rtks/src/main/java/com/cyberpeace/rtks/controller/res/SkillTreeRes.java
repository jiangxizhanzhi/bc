package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkillTreeRes implements Serializable {

	@ApiModelProperty("类别")
	private String category;

	@ApiModelProperty("技能列表")
	private List<SkillRes> skillResList;

}