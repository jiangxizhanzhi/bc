package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 练习提交请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class PracticeSubmitReq implements Serializable {

	/**
	 * 习题类型 0=理论基础 2=实操练习
	 *
	 * @see Integer
	 */
	@ApiModelProperty("习题类型 0=理论基础 2=实操练习")
    @NotNull
    private Integer practiceType;

	/**
	 * 提交的答案
	 *
	 * @see String
	 */
	@ApiModelProperty("提交的答案")
    @NotBlank
    private String submitAnswer;

	/**
	 * 习题编号
	 *
	 * @see String
	 */
	@ApiModelProperty("习题编号")
    @NotBlank
    private String practiceId;

	/**
	 * 是否需要判题
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否需要判题")
    private Boolean needEvaluate = false;

	/**
	 * 题型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("题型")
    private Integer questionType;

}
