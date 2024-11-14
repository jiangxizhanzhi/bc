package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 练习答案上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class PracticeAnswerContext implements Serializable {

	/**
	 * 习题类型 0=理论基础 2=实操练习
	 */
	@ApiModelProperty("习题类型 0=理论基础 2=实操练习")
	private Integer practiceType;

	/**
	 * 提交的答案
	 */
	@ApiModelProperty("提交的答案")
	private String submitAnswer;

	/**
	 * 习题编号
	 */
	@ApiModelProperty("习题编号")
	private String practiceId;

	/**
	 * 是否需要判题
	 */
	@ApiModelProperty("是否需要判题")
	private Boolean needEvaluate = false;

	/**
	 * 题型
	 */
	@ApiModelProperty("题型")
	private Integer questionType;

}
