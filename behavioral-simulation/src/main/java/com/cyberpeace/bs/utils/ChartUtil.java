package com.cyberpeace.bs.utils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.bs.service.bo.behavior.Behavior;
import com.cyberpeace.bs.service.bo.model.ChartResult;
import com.cyberpeace.bs.service.bo.model.NameValue;
import com.google.common.collect.Maps;

/**
 * 生成图形的辅助函数
 *
 * @author SN
 * @date 2023/05/08
 */
public class ChartUtil {

	/**
	 * 横轴数据模板：x时
	 */
	private static final String HOUR = "%d时";

	/**
	 * 一天的小时数
	 */
	private static final int HOURS_IN_A_DAY = 24;

	/**
	 * 生成活跃度坐标图
	 * @param chart 包含图表的用户模型详情结果
	 */
	public static void createXy(ChartResult chart) {
		// 默认行为已经排过序
		List<Behavior> behaviorList = chart.getBehaviorList();
		int count;
		int index = 0;
		int size = behaviorList.size();
		// 24个数据点
		for (int time = 0; time < HOURS_IN_A_DAY; time++) {
			// 重置计数
			count = 0;
			List<String> labels = Lists.newArrayList();
			// 填充x轴
			chart.getXTime().add(String.format(HOUR, time));
			// 遍历行为，获取每个小时时间段内，有多少行为
			while (index < size) {
				if (behaviorList.get(index).getStartTime().getHour() > time) {
					// 当前时段已经统计完毕
					chart.addY(count, labels);
					break;
				} else {
					labels.add(behaviorList.get(index).getName());
					count++;
					index++;
				}
			}
			// 行为循环完毕
			if (index == size) {
				chart.addY(count, labels);
			}
		}
	}

	/**
	 * 生成 tag 饼状图
	 *
	 * @param chart 图表
	 */
	public static void createPie(ChartResult chart) {
		Map<String, Integer> map = Maps.newHashMap();
		// 遍历统计各tag的出现个数
		chart.getBehaviorList().forEach(behavior -> {
			List<String> tags = behavior.getTag();
			tags.forEach(tag -> {
				if (map.containsKey(tag)) {
					map.put(tag, map.get(tag) + 1);
				} else {
					map.put(tag, 1);
				}
			});
		});
		// 遍历字典，生成列表
		List<NameValue> pieChart = Lists.newArrayList();
		map.forEach((key, value) -> pieChart.add(new NameValue(key, value)));
		// 按tag出现个数，给tag排序
		pieChart.sort(Comparator.comparing(NameValue::getValue));
		chart.setPieChart(pieChart);
	}
}