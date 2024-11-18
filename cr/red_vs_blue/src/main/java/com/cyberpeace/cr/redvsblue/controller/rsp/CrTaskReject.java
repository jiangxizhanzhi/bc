package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.Date;

import lombok.Data;

/**
 * 靶场任务拒绝
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class CrTaskReject {

	/**
	 * 拒绝时间
	 *
	 * @see Date
	 */
	private Date rejectTime;

	/**
	 * 拒绝原因
	 *
	 * @see Integer
	 */
	private Integer rejectReason;

	/**
	 * 裁判
	 *
	 * @see String
	 */
	private String referee;

}
