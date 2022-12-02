package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CenterInfoContext implements Serializable {

	@ApiModelProperty("LG中心ID")
	private String centerId;

	@ApiModelProperty("LG中心名称")
	private String centerName;

	@ApiModelProperty("LG中心位置")
	private String location;

	@ApiModelProperty("LG中心地址")
	private String address;

	@ApiModelProperty("执行中独立任务")
	private Integer independentTaskCount;

	@ApiModelProperty("执行中协同任务")
	private Integer collaborativeTaskCount;

	@ApiModelProperty("执行中联合任务")
	private Integer jointTaskCount;

	@ApiModelProperty("任务执行趋势")
	private List<CenterTaskTrendContext> taskTrend;

	public CenterInfoContext() {}

}
