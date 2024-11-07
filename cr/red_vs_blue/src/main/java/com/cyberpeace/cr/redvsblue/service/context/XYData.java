package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 二维曲线数据
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class XYData {

	/**
	 * X 数据
	 */
	@ApiModelProperty(value = "X轴数据")
	private List<String> xData;

	/**
	 * y 数据
	 */
	@ApiModelProperty(value = "Y轴数据")
	private List<Integer> yData;

	public XYData(List<String> xData, List<Integer> yData) {
		this.xData = xData;
		this.yData = yData;

	}
}
