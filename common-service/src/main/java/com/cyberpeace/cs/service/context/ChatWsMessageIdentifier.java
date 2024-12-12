package com.cyberpeace.cs.service.context;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Chat WS 消息标识符
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class ChatWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 聊天室业务编号 课堂=课堂表编号 红蓝=红蓝对抗房间编号
	 */
	private String businessId;

	/**
	 * 聊天室类型
	 */
	private Integer businessType;

}
