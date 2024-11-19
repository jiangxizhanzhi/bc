package com.cyberpeace.situation.model.ws;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * WS 消息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class WsMessage<T> {

	/**
	 * 消息编码
	 *
	 * @see String
	 */
	@ApiModelProperty("消息编码")
	private String code;

	/**
	 * 消息内容
	 *
	 * @see T
	 */
	@ApiModelProperty("消息内容")
	private T data;

	public WsMessage(String code, T data) {
		this.code = code;
		this.data = data;
	}

}
