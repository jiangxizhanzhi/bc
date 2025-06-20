package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 实践解决结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class PracticeSolvedRes implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 实践id
	 *
	 * @see String
	 */
	@ApiModelProperty("练习id")
	private String practiceId;

	/**
	 * 解决
	 *
	 * @see Integer
	 */
	@ApiModelProperty("答题状态")
	private Integer solved;
}
