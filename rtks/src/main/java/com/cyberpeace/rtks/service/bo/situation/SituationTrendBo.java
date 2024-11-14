package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.common.util.TimeUtil;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationTrendBo implements Serializable {

	/**
	 * 日期
	 */
	@ApiModelProperty("日期")
	private List<String> xDates;

	/**
	 * 人-岗数
	 */
	@ApiModelProperty("人-岗数")
	private List<Integer> yNums;

	public SituationTrendBo() {
		this.xDates = Lists.newArrayList();
		this.yNums = Lists.newArrayList();
	}

	/**
	 * 空数据
	 * @param least 最小数据量
	 */
	public SituationTrendBo(Integer least) {
		this.xDates = Lists.newArrayList();
		this.yNums = Lists.newArrayList();
		Date date = TimeUtil.addDates(new Date(), 1 - least);
		for (int i = 0; i < least; i++) {
			xDates.add(TimeUtil.format6(date));
			yNums.add(0);
			date = TimeUtil.addDates(date, 1);
		}
	}

	/**
	 * 数据量不足时填充
	 * @param least 最小数据量
	 * @param start 真实数据的开始时间
	 */
	public void fill(Integer least, Date start) {
		int shortage = least - this.xDates.size();
		if (shortage < 0) {
			return;
		}
		List<String> fillDates = Lists.newArrayList();
		List<Integer> fillNums = Lists.newArrayList();

		for (int i = -shortage; i < 0; i++) {
			Date date = TimeUtil.addDates(start, i);
			fillDates.add(TimeUtil.format6(date));
			fillNums.add(0);
		}
		fillDates.addAll(this.xDates);
		fillNums.addAll(this.yNums);
		this.xDates = fillDates;
		this.yNums = fillNums;
	}
}
