package com.cyberpeace.common.model.ws;

import lombok.Data;

/**
 * 帮助聊天消息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see ChatMessage
 * @since 1.0.0
 */
@Data
public class HelpChatMessage extends ChatMessage {

	/**
	 * 角色
	 *
	 * @see String
	 */
	private String role;

}
