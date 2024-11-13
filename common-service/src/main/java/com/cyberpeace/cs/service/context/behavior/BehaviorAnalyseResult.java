package com.cyberpeace.cs.service.context.behavior;

import lombok.Data;

/**
 * 行为分析结果
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class BehaviorAnalyseResult {

	/**
	 * 最流行课程
	 */
	private String mostPopularCourse;

	/**
	 * 最流行课时
	 */
	private String mostPopularLesson;

	/**
	 * 练习
	 */
	private PracticeResult practice;

	/**
	 * 理论
	 */
	private PracticeResult theory;
}
