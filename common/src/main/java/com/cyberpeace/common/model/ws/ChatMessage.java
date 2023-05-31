package com.cyberpeace.common.model.ws;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

	private String username;

	private String avatar;

	/**
	 * 接收者看到的聊天框(群聊为群id，私聊为发送者id)
	 */
	private String id;

	private String type;

	private String content;

	private final Integer cid = 0;

	private final Boolean mine = Boolean.FALSE;

	private String fromid;

	private String time;

	private long timestamp;

	public void setTimestamp() {
		timestamp = System.currentTimeMillis();
	}

	private String uuid;
}
