package com.cyberpeace.common.model.ws;
import lombok.Data;

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


