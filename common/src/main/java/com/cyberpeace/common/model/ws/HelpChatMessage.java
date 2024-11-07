package com.cyberpeace.common.model.ws;

import lombok.Data;

/**
 * 帮助聊天消息
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class HelpChatMessage extends ChatMessage {

	/**
	 * 角色
	 */
	private String role;

}
