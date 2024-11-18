package com.cyberpeace.cr.crbase.service.context.task;

import java.util.Date;

import lombok.Data;

/**
 * 靶场任务拒绝响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class CrTaskRejectRsp {

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
