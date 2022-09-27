package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.Date;

import lombok.Data;

@Data
public class CrTaskReject {

	private Date rejectTime;

	private Integer rejectReason;

	private String referee;

}
