package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WsMessageBO<T>  {

	@ApiModelProperty("消息编码")
	private String code;

	@ApiModelProperty("消息内容")
	private T data;

	public WsMessageBO(String code, T data) {
		this.code = code;
		this.data = data;
	}

}
