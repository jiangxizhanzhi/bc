package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CenterTaskTrendContext implements Serializable {

	@ApiModelProperty("时间")
	private Date time;

	@ApiModelProperty("执行中独立任务")
	private Integer independentTaskCount;

	@ApiModelProperty("执行中协同任务")
	private Integer collaborativeTaskCount;

	@ApiModelProperty("执行中联合任务")
	private Integer jointTaskCount;

}
