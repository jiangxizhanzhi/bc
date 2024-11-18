package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * WS 消息 bo
 *
 * @author SN
 * @see Serializable
 * @version 1.0.0
 * @since 1.0.0
 * @date 2024/11/07
 */
@Data
public class WsMessageBO<T> implements Serializable {

	/**
     * 代号
     *
     * @see String
     */
	@ApiModelProperty("消息编码")
	private String code;

	/**
     * 数据
     *
     * @see T
     */
	@ApiModelProperty("消息内容")
	private T data;

	public WsMessageBO(String code, T data) {
		this.code = code;
		this.data = data;
	}

}
