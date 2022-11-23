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
 * 生成图形
 * @author SN
 */
public class ChartUtil {

	private static final String HOUR = "%d时";

	private static final int HOURS_IN_A_DAY = 24;
	/**
	 * 生成活跃度坐标图
	 * @param chart
	 */
	public static void createXy(ChartResult chart) {
		// 默认行为已经排过序
		List<Behavior> behaviorList = chart.getBehaviorList();
		int count;
		int index = 0;
		int size = behaviorList.size();
		for (int time = 0; time < HOURS_IN_A_DAY; time++) {
			count = 0;
			List<String> labels = Lists.newArrayList();
			chart.getXTime().add(String.format(HOUR, time));
			while (index < size) {
				if (behaviorList.get(index).getStartTime().getHour() > time) {
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
	 * @param chart
	 */
	public static void createPie(ChartResult chart) {
		Map<String, Integer> map = Maps.newHashMap();

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

		List<NameValue> pieChart = Lists.newArrayList();
		map.forEach((key, value) -> pieChart.add(new NameValue(key, value)));
		pieChart.sort(Comparator.comparing(NameValue::getValue));
		chart.setPieChart(pieChart);
	}
}