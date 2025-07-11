package com.cyberpeace.cr.redvsblue.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 联合训练攻击请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class UniteAttackReq implements Serializable {

	/**
	 * 任务id
	 */
	@ApiModelProperty(value = "任务id")
	private String taskId;

	/**
	 * 源id
	 */
	@ApiModelProperty(value = "源id")
	private String attackerId;

	/**
	 * 目的id
	 */
	@ApiModelProperty(value = "目的id")
	private String targetId;

	@ApiModelProperty(value = "使用的工具")
	private String toolId;

	@ApiModelProperty(value = "开始攻击时间")
	private Date attackStarttime;

	@ApiModelProperty(value = "攻击结束时间")
	private Date attackEndtime;
}
