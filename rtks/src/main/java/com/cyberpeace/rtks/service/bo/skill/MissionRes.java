package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import com.cyberpeace.cr.crbase.service.context.mission.MissionTemplateRes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionRes extends MissionTemplateRes implements Serializable {

	/**
	 * 任务权重
	 */
	@ApiModelProperty("任务权重")
	private Integer weight;

	/**
	 * rtks技能id
	 */
	@ApiModelProperty("rtks技能id")
	private String skillId;
}
