package com.cyberpeace.cs.service.context;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Data;

/**
 * Chat WS 消息标识符
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see WsMessageIdentifier
 * @since 1.0.0
 */
@Data
public class ChatWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 聊天室业务编号 课堂=课堂表编号 红蓝=红蓝对抗房间编号
	 *
	 * @see String
	 */
	private String businessId;

	/**
	 * 聊天室类型
	 *
	 * @see Integer
	 */
	private Integer businessType;

}
