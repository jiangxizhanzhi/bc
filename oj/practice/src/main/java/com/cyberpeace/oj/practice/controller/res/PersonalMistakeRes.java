package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 个人错误结果
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class PersonalMistakeRes implements Serializable {

	/**
	 * 实践id
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
	 *
	 */
	@ApiModelProperty("答错次数")
	private Integer incorrect;

	/**
	 * 方向
	 */
	@ApiModelProperty("方向")
	private String direction;

	/**
	 * 难度
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

}
