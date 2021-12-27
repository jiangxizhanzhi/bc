package com.cyberpeace.cr.redvsblue.controller.rsp;

import lombok.Data;

@Data
public class TimeDataRsp {

	private Integer status;

	private Integer timeStamp;

	private Integer startTime;

	private Integer running;
}
