package com.cyberpeace.common.model.ws;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 网络套接字消息上下文
 *
 * @author yinqingrong
 * @date 2023/05/06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketMessageContext<I extends WsMessageIdentifier, M> implements Serializable {

	private I messageIdentifier;

	private M data;

}
