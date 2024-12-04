package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 纸质实践背景
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class PaperPracticeContext implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("习题编号")
	@NotBlank
	private String resourceId;

	/**
	 * 裁判id
	 *
	 * @see String
	 */
	@ApiModelProperty("快照编号")
	private String refId;

	/**
	 * 执业类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("习题类型")
	@NotNull
	private Integer practiceType;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	@ApiModelProperty("课后练习分值")
	@NotNull
	private Double score;

	/**
	 * 选项 random
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("选项是否随机")
	private Boolean optionRandom;

}
