package com.cyberpeace.cr.singletrain.service.bo.mission;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 矩阵任务
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MatrixMission implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "任务id")
	private String resourceId;

	/**
	 * 标题
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "任务标题")
	private String title;

	/**
	 * 状态
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "任务状态, 11:未提交， 1：成功，2：失败")
	private Integer status;
}
