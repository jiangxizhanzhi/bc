package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务下发进度
 *
 * @author SN
 * @date 2023/05/15
 */
@Data
public class TaskReleaseProgress extends ReleaseProgress implements Serializable {

	/**
	 * 未发布
	 */
	@ApiModelProperty(value = "待下发数")
	private Integer unreleased = 0;

	/**
	 * 提交
	 */
	@ApiModelProperty(value = "已提交数")
	private Integer submitted = 0;

	/**
	 * 未提交
	 */
	@ApiModelProperty(value = "待提交数")
	private Integer unsubmitted = 0;

	/**
	 * 等待
	 */
	@ApiModelProperty(value = "待评估")
	private Integer waiting = 0;

}
