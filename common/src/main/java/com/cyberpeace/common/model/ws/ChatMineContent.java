package com.cyberpeace.common.model.ws;

import lombok.Data;

/**
 * 聊天挖矿内容
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class ChatMineContent {

	/**
	 * 用户名
	 *
	 * @see String
	 */
	private String username;

	/**
	 * 化身
	 *
	 * @see String
	 */
	private String avatar;

	/**
	 * id
	 *
	 * @see String
	 */
	private String id;

	/**
	 * 矿山
	 *
	 * @see Boolean
	 */
	private Boolean mine;

	/**
	 * 内容
	 *
	 * @see String
	 */
	private String content;

	/**
	 * 时间
	 *
	 * @see String
	 */ // 后面增加的服务器时间
	private String time;

	/**
	 * uuid
	 *
	 * @see String
	 */
	private String uuid;
}
