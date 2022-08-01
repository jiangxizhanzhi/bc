package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkillProcessRes extends SkillResBase implements Serializable {

	@ApiModelProperty("完成度")
	private Integer process = 0;

	@ApiModelProperty("任务完成度")
	private List<MissionProcessBo> missions = Lists.newArrayList();
}
