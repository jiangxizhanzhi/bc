package com.cyberpeace.common.model.ws;

import lombok.Data;

/**
 * 帮助聊天消息
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class HelpChatMessage extends ChatMessage {

	/**
	 * 岗位
	 */
	private String role;

}
