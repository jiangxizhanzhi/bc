package com.cyberpeace.situation.model.ws;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WsMessage<T> {

	@ApiModelProperty("消息编码")
	private String code;

	@ApiModelProperty("消息内容")
	private T data;

	public WsMessage(String code, T data) {
		this.code = code;
		this.data = data;
	}

}
