package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;

/**
 * 流量日志分页上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class FlowLogsPageContext implements Serializable {

	/**
	 * id
	 *
	 * @see String
	 */
	private String id;

	/**
	 * 启动时间
	 *
	 * @see String
	 */
	private String startTime;

	/**
	 * 结束时间
	 *
	 * @see String
	 */
	private String endTime;

	/**
	 * 每分页
	 *
	 * @see Integer
	 */
	private Integer perPage;

	/**
	 * 分页
	 *
	 * @see Integer
	 */
	private Integer page;

	/**
	 * 排序
	 *
	 * @see String
	 */
	private String sort;

}
