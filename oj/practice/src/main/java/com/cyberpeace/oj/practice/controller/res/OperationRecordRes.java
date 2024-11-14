package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实操记录结果
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class OperationRecordRes implements Serializable {

	/**
	 * 练习id
	 */
	@ApiModelProperty("练习id")
	private String practiceId;

	/**
	 * 题目名称
	 */
	@ApiModelProperty("题目名称")
	private String title;

	/**
	 * 方向
	 */
	@ApiModelProperty("方向")
	private String categoryName;

	/**
	 * 习题集
	 */
	@ApiModelProperty("习题集")
	private String setName;

	/**
	 * 难度
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

	/**
	 * 完成状态
	 */
	@ApiModelProperty("完成状态")
	private Boolean solved;

	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
	private String time;
}
