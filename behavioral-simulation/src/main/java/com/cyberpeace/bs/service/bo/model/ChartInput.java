package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChartInput implements Serializable {

	@ApiModelProperty(value = "行为数据列表")
	private List<Behavior> behaviors;

	@ApiModelProperty(value = "访问时间配置类型")
	private Integer accessConfig;

	@ApiModelProperty(value = "访问时段开始时间")
	private LocalTime startTime;

	@ApiModelProperty(value = "访问时段结束时间")
	private LocalTime endTime;
}