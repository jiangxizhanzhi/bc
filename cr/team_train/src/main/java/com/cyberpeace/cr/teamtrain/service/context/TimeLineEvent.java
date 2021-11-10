package com.cyberpeace.cr.teamtrain.service.context;

import java.util.Date;

import lombok.Data;

@Data
public class TimeLineEvent {

	private String resourceId;

	private Date time;

	private String title;

	private String userName;

	private Integer type;
}
