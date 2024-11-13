package com.cyberpeace.cr.crbase.service.context.training;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练耗时
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class TrainingTimeCost implements Serializable {

	/**
	 * 耗时
	 */
	@ApiModelProperty(value = "训练时长，单位为秒")
	private Long timeCost = 0L;

	/**
	 * 是否正在运行
	 */
	@ApiModelProperty(value = "是否正在运行")
	private Boolean running = false;
}
