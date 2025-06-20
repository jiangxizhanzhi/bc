package com.cyberpeace.oj.practice.service.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 理论改变博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class TheoryChangeBo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 答案
	 *
	 * @see String
	 */
	private String answer;

	/**
	 * 内容
	 *
	 * @see String
	 */
	private String content;

	/**
	 * 理论类型
	 *
	 * @see Integer
	 */
	private Integer theoryType;

	/**
	 * 空白
	 *
	 * @see String
	 */
	private String blank;

	/**
	 * 选择
	 *
	 * @see String
	 */
	private String option;
}
