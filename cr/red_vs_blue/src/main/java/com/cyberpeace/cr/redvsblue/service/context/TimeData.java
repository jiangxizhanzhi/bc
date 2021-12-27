package com.cyberpeace.cr.redvsblue.service.context;

import java.util.Date;
import java.util.List;

import com.cyberpeace.common.util.TimeUtil;
import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class TimeData {

	private Integer status;

	private Date startTime;

	private Date endTime;

	private Date xMaxTime;

	private List<String> xData;

	private List<Date> timeList;

	private Integer running;

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

			this.xData = xDataList;
			this.timeList = timeList;
		}
		running = 0;
		if (this.status > 0) {
			Long long_running = (realEndTime.getTime() - startTime.getTime()) / 1000;
			running = long_running.intValue();
		}

	}
}
