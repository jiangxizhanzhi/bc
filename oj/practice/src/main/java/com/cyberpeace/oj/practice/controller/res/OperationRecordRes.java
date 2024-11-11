package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实操记录结果
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class OperationRecordRes implements Serializable {

	/**
	 * 练习id
	 */
	@ApiModelProperty("练习id")
	private String practiceId;

	/**
	 * 标题
	 */
	@ApiModelProperty("题目名称")
	private String title;

	/**
	 * 类别名称
	 */
	@ApiModelProperty("方向")
	private String categoryName;

	/**
	 * 设置名称
	 */
	@ApiModelProperty("习题集")
	private String setName;

	/**
	 * 困难
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

	/**
	 * 解决
	 */
	@ApiModelProperty("完成状态")
	private Boolean solved;

	/**
	 * 时间
	 */
	@ApiModelProperty("更新时间")
	private String time;
}
