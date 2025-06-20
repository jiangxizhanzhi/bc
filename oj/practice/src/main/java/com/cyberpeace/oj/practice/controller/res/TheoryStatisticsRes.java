package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 理论统计学结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class TheoryStatisticsRes implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 错误
	 *
	 * @see Integer
	 */
	@ApiModelProperty("错题数量")
	private Integer mistake;

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
