package com.cyberpeace.bs.utils;

import java.time.LocalTime;
import java.util.Random;

import com.cyberpeace.bs.service.bo.model.Behavior;
import com.cyberpeace.bs.service.bo.model.ChartInput;
import com.cyberpeace.common.util.MathUtils;

/**
 * 随机数相关的辅助函数
 * @author SN
 */
public class RandomUtil {

	private static final Random RANDOM = new Random();

	/**
	 * 随机分配执行时间
	 * @param chartInput 前端输入
	 */
	public static void randomDistribution(ChartInput chartInput) {
		LocalTime start = chartInput.getStartTime();
		LocalTime end = chartInput.getEndTime();
		int duration = (end.getHour() - start.getHour()) * 60
				+ (end.getMinute() - start.getMinute());
		// 分配随机的执行时间
		for (Behavior behavior : chartInput.getBehaviors()) {
			int delay = RANDOM.nextInt(duration);
			behavior.setStartTime(start.plusMinutes(delay));
		}
		// sort
		BsUtil.sortBehaviorList(chartInput.getBehaviors());
	}

	/**
	 * 生成正态分布
	 * @param chartInput
	 */
	public static void normalDistribution(ChartInput chartInput) {
		LocalTime start = chartInput.getStartTime();
		LocalTime end = chartInput.getEndTime();
		int duration = (end.getHour() - start.getHour()) * 60
				+ (end.getMinute() - start.getMinute());
		LocalTime mid = start.plusMinutes(duration / 2);
		// 粗略认为正态分布N(0,1)分布在-4~4之间
		int unit = duration / 8;
		// 分配随机的执行时间
		for (Behavior behavior : chartInput.getBehaviors()) {
			double g = RANDOM.nextGaussian();
			int shift = MathUtils.roundDivide((int) (2 * g * unit), 2);
			behavior.setStartTime(mid.plusMinutes(shift));
		}
		// sort
		BsUtil.sortBehaviorList(chartInput.getBehaviors());
	}
}
