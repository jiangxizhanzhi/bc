package com.cyberpeace.cr.crbase.service.context.task;

import java.util.Date;

import lombok.Data;

@Data
public class CrTaskRejectRsp {

	private Date rejectTime;

	private Integer rejectReason;

	private String referee;

}
