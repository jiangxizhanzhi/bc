package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 二维曲线数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class XYData {

	/**
	 * X 数据
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "X轴数据")
	private List<String> xData;

	/**
	 * y 数据
	 *
	 * @see List<Integer>
	 */
	@ApiModelProperty(value = "Y轴数据")
	private List<Integer> yData;

	public XYData(List<String> xData, List<Integer> yData) {
		this.xData = xData;
		this.yData = yData;

	}
}
