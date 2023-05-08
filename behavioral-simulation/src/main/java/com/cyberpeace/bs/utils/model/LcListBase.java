package com.cyberpeace.bs.utils.model;

import lombok.Data;

/**
 * 远程列表响应基础数据格式
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class LcListBase {

	/**
	 * 条目总数
	 */
	private Integer total;

	/**
	 * 页数
	 */
	private Integer page;

	/**
	 * 每页大小
	 */
	private Integer size;
}
