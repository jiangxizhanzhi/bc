package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import com.cyberpeace.cr.crbase.service.context.mission.MissionTemplateRes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务结果
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class MissionRes extends MissionTemplateRes implements Serializable {

	/**
	 * 重量
	 */
	@ApiModelProperty("任务权重")
	private Integer weight;
}
