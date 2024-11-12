package com.cyberpeace.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 模拟实际需要发送的websocket消息
 *
 * @author admin
 * @date 2024/11/12
 */
@Data
@Builder
public class DemoWsMessage implements Serializable {

	/**
	 * 时间戳
	 */
	private Date timestamp;

	/**
	 * 代码
	 */
	private Integer code;

	/**
	 * 消息
	 */
	private String message;
}
