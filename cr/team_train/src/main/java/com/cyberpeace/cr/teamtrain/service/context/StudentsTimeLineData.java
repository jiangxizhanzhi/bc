package com.cyberpeace.cr.teamtrain.service.context;

import java.util.Date;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.mission.TimeLineEvent;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 学员时间线数据
 */
@Data
public class StudentsTimeLineData {

	@ApiModelProperty(value = "时间线事件")
	private List<TimeLineEvent> data;

	@ApiModelProperty(value = "当前时间")
	private Date currentTime;

	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	private Date endTime;
}