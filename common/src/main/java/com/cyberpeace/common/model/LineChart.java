package com.cyberpeace.common.model;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 折线图
 * @author SN
 * @date 2023/09/06
 */
@Data
public class LineChart implements Serializable {

	@ApiModelProperty(value = "x轴")
	List<String> xAxis = Lists.newArrayList();

	@ApiModelProperty(value = "y轴")
	List<Integer> yAxis = Lists.newArrayList();

	public LineChart addDot(String x, Integer y) {
		this.xAxis.add(x);
		this.yAxis.add(y);
		return this;
	}
}
