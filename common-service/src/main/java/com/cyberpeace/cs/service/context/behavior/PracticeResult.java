package com.cyberpeace.cs.service.context.behavior;

import lombok.Data;

/**
 * 练习结果
 *
 * @author SN
 * @date 2024/05/21
 */
@Data
public class PracticeResult {

	/**
	 * 最多错误
	 */
	private String mostWrong;

	/**
	 * 最多回答
	 */
	private String mostAnswered;

	/**
	 * 最正确
	 */
	private String mostCorrect;

	/**
	 * 最受喜爱
	 */
	private String mostFavorite;
}
