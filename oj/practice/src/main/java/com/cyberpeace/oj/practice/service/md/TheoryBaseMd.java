package com.cyberpeace.oj.practice.service.md;

import lombok.Data;

/**
 * 理论基础markdown
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class TheoryBaseMd {

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 难度
	 */
	private String difficulty;

	/**
	 * 答案
	 */
	private String answer;

	/**
	 * 串行id
	 */
	private Integer serialId;

	/**
	 * 类别
	 */
	private String category;

}
