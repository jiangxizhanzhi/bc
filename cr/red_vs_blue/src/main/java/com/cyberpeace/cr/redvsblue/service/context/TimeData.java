package com.cyberpeace.cr.redvsblue.service.context;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.redvsblue.service.context.mission.ObjWithTime;
import com.google.common.collect.Lists;

import lombok.Data;

/**
 * 时间数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class TimeData {

	/**
	 * 状态
	 *
	 * @see Integer
	 */
	private Integer status;

	/**
	 * 开始时间
	 *
	 * @see Date
	 */
	private Date startTime;

	/**
	 * 结束时间
	 *
	 * @see Date
	 */
	private Date endTime;

	/**
	 * x 最大时间
	 *
	 * @see Date
	 */
	private Date xMaxTime;

	/**
	 * X 数据
	 *
	 * @see List<String>
	 */
	private List<String> xData;

	/**
	 * 时间列表
	 *
	 * @see List<Date>
	 */
	private List<Date> timeList;

	/**
	 * 运行
	 *
	 * @see Integer
	 */
	private Integer running;

	/**
	 * 积累值
	 *
	 * @see Integer
	 */
	private Integer accumulatedValue;

	public TimeData(int status, Date startTime, Date realEndTime, Date supposedEndTime) {
		Integer step = 60;

		this.status = status;
		this.startTime = startTime;
		this.endTime = realEndTime;
		this.xMaxTime = realEndTime.before(supposedEndTime) ? supposedEndTime : realEndTime;

		List<String> xDataList = Lists.newArrayList();
		Long times = (realEndTime.getTime() - startTime.getTime()) / 1000 / step;
		List<Date> timeList = Lists.newArrayList();
		if (times < 1) {
			timeList.add(realEndTime);
		} else {
			timeList.add(startTime);

			Date itemTime = startTime;
			for (int i = 0; i < times.intValue(); i++) {
				itemTime = TimeUtil.addMins(itemTime, step / 60);
				timeList.add(itemTime);

				// 计算时长
				Integer hours = (step / 60) * (i + 1) / 60;
				Integer minutes = (step / 60) * (i + 1) % 60;

				xDataList.add(hours + ":" + String.format("%02d", minutes));

			}
			timeList.add(realEndTime);

		}

		this.xData = xDataList;
		this.timeList = timeList;
		running = 0;
		if (this.status > 0) {
			Long longRunning = (realEndTime.getTime() - startTime.getTime()) / 1000;
			running = longRunning.intValue();
		}

	}

	/**  按时间统计数据变化趋势
	 * @param objWithTimes
	 * @return
	 */
	public List<Integer> calculateCountByTime(List<ObjWithTime> objWithTimes) {
		// 统计时间数据
		List<Integer> yData = Lists.newArrayList();
		// 索引指针
		Integer pointIndex = 0;
		// 累加值
		Integer accumulatedValue = 0;

		Collections.sort(objWithTimes);

		for (Date timePoint : timeList) {
			// 无统计数据产生时（未运行/未产生任务数据）只做x轴坐标显示，y轴数据全为0
			if (objWithTimes != null) {
				// point_index指向data最后一位时，不再进行统计。
				if (pointIndex <= objWithTimes.size() - 1) {
					for (int index = pointIndex; index < objWithTimes.size(); index++) {
						Date itemTime = objWithTimes.get(index).getTime();
						if (itemTime.after(timePoint)) {
							// 下次循环的索引位置
							pointIndex = index;
							break;
						}
						// 指定统计字段，否则统计次数
						accumulatedValue += objWithTimes.get(index).getValue();

						// 统计到最后一位
						if (index == objWithTimes.size() - 1) {
							pointIndex = index + 1;

						}
					}

				}
				// 超出指定结束时间部分的x轴数据添加None
				if (this.endTime != null && timePoint.after(this.endTime)) {
					yData.add(0);
				} else {
					yData.add(accumulatedValue);
				}
			}
		}
		this.accumulatedValue = accumulatedValue;
		return yData;
	}
}
