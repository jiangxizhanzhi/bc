package com.cyberpeace.common.model.ws;

import lombok.Data;

@Data
public class ChatMineContent {

	private String name;

	private String username;

	private String avatar;

	private String id;

	private Boolean mine;

	private String content;

	// 后面增加的服务器时间
	private String time;

	private String uuid;
}
