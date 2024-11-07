package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * WS 消息 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class WsMessageBO<T> implements Serializable {

	/**
	 * 代号
	 */
	@ApiModelProperty("消息编码")
	private String code;

	/**
	 * 数据
	 */
	@ApiModelProperty("消息内容")
	private T data;

	public WsMessageBO(String code, T data) {
		this.code = code;
		this.data = data;
	}

}
