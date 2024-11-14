package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估系统变量结果响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalSystemVariableResultRsp {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID")
	private String resourceId;

	/**
	 * 执行轮次
	 */
	@ApiModelProperty(value = "执行轮次")
	private Integer exeCycle;

	/**
	 * 一级得分
	 */
	@ApiModelProperty(value = "一级得分")
	private Integer firstScore;

	/**
	 * 二级得分
	 */
	@ApiModelProperty(value = "二级得分")
	private Integer secondScore;

	/**
	 * 三级得分
	 */
	@ApiModelProperty(value = "三级得分")
	private Integer thirdScore;

	/**
	 * 四级得分
	 */
	@ApiModelProperty(value = "四级得分")
	private Integer fourthScore;

	/**
	 * 五级得分
	 */
	@ApiModelProperty(value = "五级得分")
	private Integer fifthScore;

}