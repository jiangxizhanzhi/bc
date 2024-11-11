package com.cyberpeace.oj.practice.controller.res;

import com.cyberpeace.oj.practice.service.bo.ProgressProportion;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 进展统计学结果
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class ProgressStatisticsRes implements Serializable {

	/**
	 * 简单
	 */
	@ApiModelProperty("简单")
	private ProgressProportion simple;

	/**
	 * 中等
	 */
	@ApiModelProperty("中等")
	private ProgressProportion medium;

	/**
	 * 困难
	 */
	@ApiModelProperty("困难")
	private ProgressProportion difficulty;

	/**
	 * 和
	 */
	@ApiModelProperty("总共")
	private ProgressProportion sum;
}
