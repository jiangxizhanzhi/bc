package com.cyberpeace.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * 模拟实际需要发送的websocket消息
 *
 * @author SN
 * @version 1.0.0
 * @see Serializable
 * @since 1.0.0
 * @date 2024/11/19
 */
@Data
@Builder
public class DemoWsMessage implements Serializable {

	/**
	 * 时间戳
	 *
	 * @see Date
	 */
	private Date timestamp;

	/**
	 * 代号
	 *
	 * @see Integer
	 */
	private Integer code;

	/**
	 * 消息
	 *
	 * @see String
	 */
	private String message;
}
