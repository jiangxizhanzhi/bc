package com.cyberpeace.common.model.ws;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 帮助聊天消息
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class HelpChatMessage extends ChatMessage {

	/**
	 * 岗位
	 */
	private String role;

}
