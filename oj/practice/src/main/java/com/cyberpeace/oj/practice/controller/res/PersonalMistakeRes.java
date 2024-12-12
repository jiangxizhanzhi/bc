package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 个人错误结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class PersonalMistakeRes implements Serializable {

	/**
	 * 实践id
	 *
	 * @see String
	 */
	@ApiModelProperty("题目id")
	private String practiceId;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty("题目名称")
	private String content;

	/**
	 * 理论类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("题目类型")
	private Integer theoryType;

	/**
	 * 正确
	 *
	 * @see Integer
	 */
	@ApiModelProperty("答对次数")
	private Integer correct;

	/**
	 * 不對
	 *
	 * @see Integer
	 */
	@ApiModelProperty("答错次数")
	private Integer incorrect;

	/**
	 * 方向
	 *
	 * @see String
	 */
	@ApiModelProperty("方向")
	private String direction;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

}
