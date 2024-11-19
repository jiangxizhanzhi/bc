package com.cyberpeace.rtks.util;

/**
 * 加权计算
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
public class WeightedCalculation {

	/**
	 * 权重和
	 *
	 * @see Integer
	 */
	private Integer weightSum = 0;

	/**
	 * 分数总和
	 *
	 * @see Double
	 */
	private Double scoreSum = 0.0;

	/**
	 * 饲料
	 *
	 * @param weight 权重
	 * @param score  得分
	 * @throws
	 * @see Integer
	 * @see Double
	 */
	public void feed(Integer weight, Double score) {
		weightSum += weight;
		scoreSum += weight * score;
	}

	/**
	 * 解决
	 *
	 * @return {@code Double }
	 * @throws
	 * @see Double
	 */
	public Double solve() {
		if (scoreSum == 0.0) {
			return 0.0;
		} else {
			return scoreSum / weightSum;
		}
	}
}
