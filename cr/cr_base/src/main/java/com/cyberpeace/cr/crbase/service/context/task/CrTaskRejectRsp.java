package com.cyberpeace.cr.crbase.service.context.task;

import java.util.Date;

import lombok.Data;

/**
 * 靶场任务拒绝响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class CrTaskRejectRsp {

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
