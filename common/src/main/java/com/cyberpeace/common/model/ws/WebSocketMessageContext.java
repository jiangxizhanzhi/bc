package com.cyberpeace.common.model.ws;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Web 套接字消息上下文
 *
 * @author yinqingrong
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketMessageContext<I extends WsMessageIdentifier, M> implements Serializable {

	/**
	 * 消息标识符
	 *
	 * @see I
	 */
	private I messageIdentifier;

	/**
	 * 数据
	 *
	 * @see M
	 */
	private M data;

}
