package com.cyberpeace.oj.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRsp<T> {

	public T data;

	public String code = "0";

	public String message;

	/**
	 * 保留字段
	 */
	public String detail;

	public BaseRsp(T data) {
		this.data = data;
	}
}
