package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 时间轴数据
 * 兼容不同训练类型的前端事件列表字段
 * @author SN
 */
@Data
public class TimeLineData {

	@ApiModelProperty(value = "时间轴事件")
	private List<TimeLineEvent> events;

	@ApiModelProperty(value = "时间轴事件")
	private List<TimeLineEvent> data;

	@ApiModelProperty(value = "时间轴事件")
	private List<TimeLineEvent> userTimeLineConfig;

	@ApiModelProperty(value = "当前时间")
	private Date currentTime;

	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	private Date endTime;
}
