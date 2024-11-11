package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务流量程 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class MissionProcessBo extends MissionTemplateSimple implements Serializable {

	/**
	 * 过程
	 */
	@ApiModelProperty("完成度")
	private Integer process = 0;

	/**
	 * 状态
	 */
	@ApiModelProperty("0：未完成； 1：已完成")
	private Integer status = 0;
}
