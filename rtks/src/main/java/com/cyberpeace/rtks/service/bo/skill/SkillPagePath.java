package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkillPagePath extends SkillResBase implements Serializable {

	/**
	 * 任务列表
	 */
	@ApiModelProperty("任务列表")
	private List<MissionPathPageBo> missions = Lists.newArrayList();
}
