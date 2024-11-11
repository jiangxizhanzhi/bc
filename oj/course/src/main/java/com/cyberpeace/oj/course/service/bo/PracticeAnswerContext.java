package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 练习答案上下文
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class
PracticeAnswerContext implements Serializable {

	/**
	 * 练习类型
	 */
	@ApiModelProperty("习题类型 0=理论基础 2=实操练习")
	private Integer practiceType;

	/**
	 * 提交答案
	 */
	@ApiModelProperty("提交的答案")
	private String submitAnswer;

	/**
	 * 练习id
	 */
	@ApiModelProperty("习题编号")
	private String practiceId;

	/**
	 * 需要评估
	 */
	@ApiModelProperty("是否需要判题")
	private Boolean needEvaluate = false;

	/**
	 * 问题类型
	 */
	@ApiModelProperty("题型")
	private Integer questionType;

}
