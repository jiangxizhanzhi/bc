package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;

/**
 * 流原木分页上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class FlowLogsPageContext implements Serializable {

	/**
	 * id
	 */
	private String id;

	/**
	 * 开始时间
	 */
	private String startTime;

	/**
	 * 结束时间
	 */
	private String endTime;

	/**
	 * 每分页
	 */
	private Integer perPage;

	/**
	 * 分页
	 */
	private Integer page;

	/**
	 * 排序
	 */
	private String sort;

}
