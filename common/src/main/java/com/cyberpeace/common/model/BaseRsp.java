package com.cyberpeace.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基础响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRsp<T> {

	/**
	 * 数据
	 */
	public T data;

	/**
	 * 代号
	 */
	public String code = "OJ-000000";

	/**
	 * 消息
	 */
	public String message;

	/**
	 * 保留字段
	 */
	public String detail;

	/**
	 * 时间戳
	 */
//	public long timestamp = System.currentTimeMillis();

	public BaseRsp(T data) {
		this.data = data;
	}
}
