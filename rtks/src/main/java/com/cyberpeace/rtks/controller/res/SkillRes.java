package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.rtks.service.bo.skill.MissionRes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkillRes implements Serializable {

	@ApiModelProperty("技能id")
	private String resourceId;

	@ApiModelProperty("技能名称")
	private String content;

	@ApiModelProperty("技能关联任务")
	private List<MissionRes> missionList;
}