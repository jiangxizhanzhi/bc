package com.cyberpeace.common.model.ws;

import lombok.Data;

/**
 * 聊天挖矿内容
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class ChatMineContent {

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 化身
	 */
	private String avatar;

	/**
	 * id
	 */
	private String id;

	/**
	 * 矿山
	 */
	private Boolean mine;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 时间
	 */// 后面增加的服务器时间
	private String time;

	/**
	 * uuid
	 */
	private String uuid;
}
