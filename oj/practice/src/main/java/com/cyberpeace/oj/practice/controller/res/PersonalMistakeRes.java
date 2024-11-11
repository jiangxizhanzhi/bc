package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 个人错误结果
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class PersonalMistakeRes implements Serializable {

	/**
	 * 练习id
	 */
	@ApiModelProperty("题目id")
	private String practiceId;

	/**
	 * 内容
	 */
	@ApiModelProperty("题目名称")
	private String content;

	/**
	 * 理论类型
	 */
	@ApiModelProperty("题目类型")
	private Integer theoryType;

	/**
	 * 正确
	 */
	@ApiModelProperty("答对次数")
	private Integer correct;

	/**
	 * 不對
	 */
	@ApiModelProperty("答错次数")
	private Integer incorrect;

	/**
	 * 方向
	 */
	@ApiModelProperty("方向")
	private String direction;

	/**
	 * 困难
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

}
