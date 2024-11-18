package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务下发进度
 *
 * @author SN
 * @version 1.0.0
 * @date 2023/05/15
 * @see ReleaseProgress
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TaskReleaseProgress extends ReleaseProgress implements Serializable {

	/**
	 * 未发布
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "待下发数")
	private Integer unreleased = 0;

	/**
	 * 提交
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "已提交数")
	private Integer submitted = 0;

	/**
	 * 未提交
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "待提交数")
	private Integer unsubmitted = 0;

	/**
	 * 等待
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "待评估")
	private Integer waiting = 0;

}
