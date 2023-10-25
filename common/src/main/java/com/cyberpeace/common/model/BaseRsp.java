package com.cyberpeace.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRsp<T> {

	private T data;

	private String code = "OJ-000000";

	private String message;

	/**
	 * 保留字段
	 */
	private String detail;

	private long timestamp = System.currentTimeMillis();

	public BaseRsp(T data) {
		this.data = data;
	}
}
