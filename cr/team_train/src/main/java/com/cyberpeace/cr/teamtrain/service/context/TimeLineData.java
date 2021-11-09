package com.cyberpeace.cr.teamtrain.service.context;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class TimeLineData {

	private List<TimeLineEvent> userTimeLineConfig;

	private Date currentTime;

	private Date startTime;

	private Date endTime;
}
