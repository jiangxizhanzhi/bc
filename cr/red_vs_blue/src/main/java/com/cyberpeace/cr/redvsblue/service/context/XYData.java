package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 二维曲线数据
 */
@Data
public class XYData {

	/**
	 * X轴数据
	 */
	@ApiModelProperty(value = "X轴数据")
	private List<String> xData;

	/**
	 * Y轴数据
	 */
	@ApiModelProperty(value = "Y轴数据")
	private List<Integer> yData;

	public XYData(List<String> xData, List<Integer> yData) {
		this.xData = xData;
		this.yData = yData;

	}
}
