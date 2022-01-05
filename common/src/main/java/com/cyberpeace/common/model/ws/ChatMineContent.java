package com.cyberpeace.common.model.ws;
import lombok.Data;

@Data
public class ChatMineContent {
	private String username;
	private String avatar;
	private String id;
	private Boolean mine;
	private String content;
}
