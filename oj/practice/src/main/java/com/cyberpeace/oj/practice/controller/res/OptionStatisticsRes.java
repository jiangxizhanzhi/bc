package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 选择统计学结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class OptionStatisticsRes implements Serializable {

	/**
	 * 收集
	 *
	 * @see Integer
	 */
	@ApiModelProperty("收藏数量")
	private Integer collection;

	/**
	 * 注意
	 *
	 * @see Integer
	 */
	@ApiModelProperty("笔记数量")
	private Integer note;
}
