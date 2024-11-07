package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.Date;

import lombok.Data;

/**
 * 靶场任务拒绝
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class CrTaskReject {

	/**
	 * 拒绝时间
	 */
	private Date rejectTime;

	/**
	 * 拒绝原因
	 */
	private Integer rejectReason;

	/**
	 * 裁判
	 */
	private String referee;

}
