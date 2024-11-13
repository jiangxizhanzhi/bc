package com.cyberpeace.cr.crbase.service.context.task;

import java.util.Date;

import lombok.Data;

/**
 * 靶场任务驳回响应
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class CrTaskRejectRsp {

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
