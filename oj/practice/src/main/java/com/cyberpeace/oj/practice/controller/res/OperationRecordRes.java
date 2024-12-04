package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实操记录结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class OperationRecordRes implements Serializable {

	/**
	 * 实践id
	 *
	 * @see String
	 */
	@ApiModelProperty("练习id")
	private String practiceId;

	/**
	 * 标题
	 *
	 * @see String
	 */
	@ApiModelProperty("题目名称")
	private String title;

	/**
	 * 类别名称
	 *
	 * @see String
	 */
	@ApiModelProperty("方向")
	private String categoryName;

	/**
	 * 设置名称
	 *
	 * @see String
	 */
	@ApiModelProperty("习题集")
	private String setName;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

	/**
	 * 解决
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("完成状态")
	private Boolean solved;

	/**
	 * 时间
	 *
	 * @see String
	 */
	@ApiModelProperty("更新时间")
	private String time;
}
