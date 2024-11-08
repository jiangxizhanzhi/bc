package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 理论改变博
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class TheoryChangeBo implements Serializable {

	/**
	 * 答案
	 */
	private String answer;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 理论类型
	 */
	private Integer theoryType;

	/**
	 * 空白
	 */
	private String blank;

	/**
	 * 选择
	 */
	private String option;
}
