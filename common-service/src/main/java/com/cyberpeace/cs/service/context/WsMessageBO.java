package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ws消息
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class WsMessageBO<T> implements Serializable {

	@ApiModelProperty("消息编码")
	private String code;

	@ApiModelProperty("消息内容")
	private T data;

	/**
	 * ws消息
	 *
	 * @param code 代码
	 * @param data 数据
	 */
	public WsMessageBO(String code, T data) {
		this.code = code;
		this.data = data;
	}

}
