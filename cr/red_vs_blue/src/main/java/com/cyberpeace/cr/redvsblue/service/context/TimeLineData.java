package com.cyberpeace.cr.redvsblue.service.context;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class TimeLineData {

	private List<TimeLineEvent> data;

	private Date currentTime;

	private Date startTime;

	private Date endTime;
}
