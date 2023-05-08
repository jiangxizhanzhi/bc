package com.cyberpeace.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * 演示ws消息
 * 模拟实际需要发送的websocket消息
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
@Builder
public class DemoWsMessage implements Serializable {

	private Date timestamp;

	private Integer code;

	private String message;
}
