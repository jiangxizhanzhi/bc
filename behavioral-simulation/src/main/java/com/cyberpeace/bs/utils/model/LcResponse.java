package com.cyberpeace.bs.utils.model;

import lombok.Data;

/**
 * 远程响应数据格式
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class LcResponse<T> {

	/**
	 * 数据
	 */
	public T data;

	/**
	 * 响应码
	 */
	public String code;

	/**
	 * 附带信息
	 */
	public String msg;
}
