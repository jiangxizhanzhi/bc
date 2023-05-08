package com.cyberpeace.common.model.ws;

import lombok.Data;

/**
 * 聊天内容
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class ChatContent {

	private ChatToContent to;

	private ChatMineContent mine;

	public boolean isGroup() {
		return to.getType().equals("group");
	}

	public String getGroupId() {
		return to.getId();
	}

	public String getReceiverId() {
		return to.getId();
	}

	public String getMineId() {
		return mine.getId();
	}

}
