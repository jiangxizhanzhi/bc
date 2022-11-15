package com.cyberpeace.bs.utils;

import java.util.List;

import com.cyberpeace.bs.service.bo.model.Behavior;
import com.cyberpeace.bs.service.bo.model.ChartResult;

/**
 * 生成图形
 * @author SN
 */
public class ChartUtil {

	private static final String HOUR = "%d时";

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
		for (int time = 0; time < 24; time++) {
			count = 0;
			chart.getXTime().add(String.format(HOUR, time));
			while (index < size) {
				if (behaviorList.get(index).getStartTime().getHour() > time) {
					chart.getYNum().add(count);
					break;
				} else {
					count++;
					index++;
				}
			}
			// 行为循环完毕
			if (index == size) {
				chart.getYNum().add(count);
			}
		}
	}

	public static void createPie(ChartResult chart) {

	}
}
