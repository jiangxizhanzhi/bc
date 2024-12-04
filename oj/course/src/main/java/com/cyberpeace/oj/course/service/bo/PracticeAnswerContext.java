package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实践答案上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class PracticeAnswerContext implements Serializable {

	/**
	 * 执业类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("习题类型 0=理论基础 2=实操练习")
	private Integer practiceType;

	/**
	 * 提交答案
	 *
	 * @see String
	 */
	@ApiModelProperty("提交的答案")
	private String submitAnswer;

	/**
	 * 实践id
	 *
	 * @see String
	 */
	@ApiModelProperty("习题编号")
	private String practiceId;

	/**
	 * 需要评估
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否需要判题")
	private Boolean needEvaluate = false;

	/**
	 * 问题类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("题型")
	private Integer questionType;

}
