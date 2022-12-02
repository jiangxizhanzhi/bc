package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubRangeInfoContext implements Serializable {

	@ApiModelProperty("分靶场ID")
	private String subRangeId;

	@ApiModelProperty("分靶场名称")
	private String subRangeName;

	@ApiModelProperty("分靶场类型")
	private String subRangeType;

	@ApiModelProperty("大屏标题")
	private String title;

	@ApiModelProperty("分靶场位置")
	private String location;

	@ApiModelProperty("分靶场地址")
	private String address;

	@ApiModelProperty("场站数量")
	private Integer stationCount;

	@ApiModelProperty("资源数量")
	private Integer resourceCount;

	@ApiModelProperty("靶场力量")
	private Integer powerCount;

	@ApiModelProperty("执行任务数量")
	private Integer currentTaskCount;

	@ApiModelProperty("任务执行趋势")
	private List<TaskTrendContext> taskTrend;

	public SubRangeInfoContext() {}

}
