package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务过程Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see MissionTemplateSimple
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MissionProcessBo extends MissionTemplateSimple implements Serializable {

	/**
	 * 完成度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成度")
	private Integer process = 0;

	/**
	 * 0：未完成； 1：已完成
	 *
	 * @see Integer
	 */
	@ApiModelProperty("0：未完成； 1：已完成")
	private Integer status = 0;
}
