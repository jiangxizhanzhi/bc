package com.cyberpeace.oj.practice.controller.res;

import com.cyberpeace.oj.practice.service.bo.ProgressProportion;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 进展统计学结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ProgressStatisticsRes implements Serializable {

	/**
	 * 简单
	 *
	 * @see ProgressProportion
	 */
	@ApiModelProperty("简单")
	private ProgressProportion simple;

	/**
	 * 中等
	 *
	 * @see ProgressProportion
	 */
	@ApiModelProperty("中等")
	private ProgressProportion medium;

	/**
	 * 难度
	 *
	 * @see ProgressProportion
	 */
	@ApiModelProperty("难度")
	private ProgressProportion difficulty;

	/**
	 * 和
	 *
	 * @see ProgressProportion
	 */
	@ApiModelProperty("总共")
	private ProgressProportion sum;
}
