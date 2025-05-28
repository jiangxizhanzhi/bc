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
 * @date 2025/05/28
 * @see Serializable
 * @since 1.0.0
 */
@Data
@Builder
public class DemoWsMessage implements Serializable {

	private Date timestamp;

	private Integer code;

	private String message;
}
