package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 选择统计学结果
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class OptionStatisticsRes implements Serializable {

	/**
	 * 收集
	 */
	@ApiModelProperty("收藏数量")
	private Integer collection;

	/**
	 * 注意
	 */
	@ApiModelProperty("笔记数量")
	private Integer note;
}
