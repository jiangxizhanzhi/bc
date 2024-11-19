package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import com.cyberpeace.cr.crbase.service.context.mission.MissionTemplateRes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see MissionTemplateRes
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MissionRes extends MissionTemplateRes implements Serializable {

	/**
	 * 任务权重
	 *
	 * @see Integer
	 */
	@ApiModelProperty("任务权重")
	private Integer weight;

	/**
	 * rtks技能id
	 *
	 * @see String
	 */
	@ApiModelProperty("rtks技能id")
	private String skillId;
}
