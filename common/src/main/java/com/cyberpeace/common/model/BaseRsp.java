package com.cyberpeace.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基地负责
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
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

	/**
	 * 基本信息
	 *
	 * @param data 数据
	 */
	public BaseRsp(T data) {
		this.data = data;
	}

	/**
	 * 基本信息
	 *
	 * @param data    数据
	 * @param message 消息
	 */
	public BaseRsp(T data, String message) {
		this.data = data;
		this.message = message;
	}

	/**
	 * 设置信息
	 *
	 * @param msg 信息
	 */
	public void setMsg(String msg) {
		this.message = msg;
	}
}
