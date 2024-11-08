package com.cyberpeace.common.model.ws;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Web 套接字消息上下文
 *
 * @author yinqingrong@date 2024/11/08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketMessageContext<I extends WsMessageIdentifier, M> implements Serializable {

	/**
	 * 消息标识符
	 */
	private I messageIdentifier;

	/**
	 * 数据
	 */
	private M data;

}
