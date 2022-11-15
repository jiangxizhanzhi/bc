package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 行为的时间分配和标签百分比
 * @author SN
 */
@Data
public class ChartResult implements Serializable {

	@ApiModelProperty(value = "横轴-时间")
	private List<LocalTime> xTime;

	@ApiModelProperty(value = "纵轴-步骤数")
	private List<Integer> yStep;
}
