package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 理论统计学结果
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class TheoryStatisticsRes implements Serializable {

	/**
	 * 错题数量
	 */
	@ApiModelProperty("错题数量")
	private Integer mistake;

	/**
	 * 收藏数量
	 */
	@ApiModelProperty("收藏数量")
	private Integer collection;

	/**
	 * 笔记数量
	 */
	@ApiModelProperty("笔记数量")
	private Integer note;
}
