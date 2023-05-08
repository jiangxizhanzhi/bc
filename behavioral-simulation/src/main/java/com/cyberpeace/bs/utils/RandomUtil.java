package com.cyberpeace.bs.utils;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.cyberpeace.bs.service.bo.behavior.Behavior;
import com.cyberpeace.bs.service.bo.model.ChartInput;
import com.google.common.collect.Sets;
import org.apache.commons.compress.utils.Lists;

/**
 * 随机数相关的辅助函数
 * @author SN
 */
public class RandomUtil {

	/**
	 * 粗略认为标准正态分布N(0,1)分布在-4~4之间
	 */
	private static final int RANGE = 4;

	private static final Random RANDOM = new Random();

	private RandomUtil() {}

	/**
	 * 随机分配执行时间
	 * @param chartInput 前端输入
	 */
	public static void randomDistribute(ChartInput chartInput) {
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
	 * 生成粗略的正态分布
	 * @param chartInput
	 */
	public static void normalDistribute(ChartInput chartInput) {
		LocalTime start = chartInput.getStartTime();
		LocalTime end = chartInput.getEndTime();
		int duration = (end.getHour() - start.getHour()) * 60
				+ (end.getMinute() - start.getMinute());
		int unit = duration / (2 * RANGE);
		// 分配随机的执行时间
		for (Behavior behavior : chartInput.getBehaviors()) {
			int shift = BsUtil.round((RANDOM.nextGaussian() + RANGE) * unit);
			behavior.setStartTime(start.plusMinutes(shift));
		}
		// sort
		BsUtil.sortBehaviorList(chartInput.getBehaviors());
	}

	/**
	 * 随机查询
	 *
	 * @param elements 元素
	 * @param num      数量
	 * @return {@code List<T>}
	 */
	public static <T> List<T> randomSelect(List<T> elements, Integer num){
		int size = elements.size();
		if(size <= num){
			return elements;
		}
		List<T> res = Lists.newArrayList();
		Set<Integer> selected = Sets.newHashSet();
		while(res.size() < num) {
			Integer r = RANDOM.nextInt(size);
			if(!selected.contains(r)){
				selected.add(r);
				res.add(elements.get(r));
			}
		}
		return res;
	}
}
