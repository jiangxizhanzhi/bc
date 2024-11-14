package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.Date;

import lombok.Data;

/**
 * 靶场任务驳回
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class CrTaskReject {

	/**
	 * 驳回时间
	 */
	private Date rejectTime;

	/**
	 * 驳回原因
	 */
	private Integer rejectReason;

	/**
	 * 裁判
	 */
	private String referee;

}
