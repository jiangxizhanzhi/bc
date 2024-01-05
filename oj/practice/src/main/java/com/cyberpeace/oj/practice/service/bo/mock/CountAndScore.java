package com.cyberpeace.oj.practice.service.bo.mock;

import java.util.List;

import lombok.Data;

@Data
public class CountAndScore {

	private Integer type;

	/**
	 * 计数
	 */
	private Integer count;

	/**
	 * 单题分数
	 */
	private Integer score;

	private List<String> practiceIds;
}
