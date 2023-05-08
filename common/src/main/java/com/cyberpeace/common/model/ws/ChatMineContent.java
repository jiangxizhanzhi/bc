package com.cyberpeace.common.model.ws;

import lombok.Data;

/**
 * 我聊天内容
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class ChatMineContent {

	private String username;

	private String avatar;

	private String id;

	private Boolean mine;

	private String content;

	// 后面增加的服务器时间
	private String time;
}
