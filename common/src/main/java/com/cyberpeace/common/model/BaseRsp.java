package com.cyberpeace.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRsp<T> {

	public T data;

	public String code = "OJ-000000";

	public String message;

	/**
	 * 保留字段
	 */
	public String detail;

	public long timestamp = System.currentTimeMillis();

	public BaseRsp(T data) {
		this.data = data;
	}

	public void setMsg(String msg) {
		this.message = msg;
	}
}
